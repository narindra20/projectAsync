package hei.school.demo.service.event;

import static java.io.File.createTempFile;

import hei.school.demo.endpoint.event.model.ImageUploadedRequested;
import hei.school.demo.file.bucket.BucketComponent;
import hei.school.demo.mail.Email;
import hei.school.demo.mail.Mailer;
import jakarta.mail.internet.InternetAddress;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ImageUploadedRequestedService implements Consumer<ImageUploadedRequested> {

  private static final Duration LINK_EXPIRATION = Duration.ofDays(7);

  private final BucketComponent bucketComponent;
  private final Mailer mailer;

  @SneakyThrows
  @Override
  public void accept(ImageUploadedRequested event) {
    File originalFile = bucketComponent.download(event.getOriginalBucketKey());

    BufferedImage originalImage = ImageIO.read(originalFile);
    BufferedImage grayImage = toGrayscale(originalImage);

    String extension = extractExtension(event.getOriginalBucketKey());
    File grayFile = createTempFile("gray-", "." + extension);
    ImageIO.write(grayImage, extension, grayFile);

    String grayBucketKey = "grayscale/" + event.getImageId() + "." + extension;
    bucketComponent.upload(grayFile, grayBucketKey);

    String downloadLink = bucketComponent.presign(grayBucketKey, LINK_EXPIRATION).toString();

    mailer.accept(
        new Email(
            new InternetAddress(event.getEmail()),
            List.of(),
            List.of(),
            "Votre image en noir et blanc est prête",
            buildEmailBody(downloadLink),
            List.of()));

    log.info("Grayscale image generated and email sent for image {}", event.getImageId());
  }

  private BufferedImage toGrayscale(BufferedImage original) {
    BufferedImage grayImage =
        new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
    var graphics = grayImage.getGraphics();
    graphics.drawImage(original, 0, 0, null);
    graphics.dispose();
    return grayImage;
  }

  private String extractExtension(String bucketKey) {
    String[] parts = bucketKey.split("\\.");
    return parts.length > 1 ? parts[parts.length - 1] : "png";
  }

  private String buildEmailBody(String downloadLink) {
    return "<p>Bonjour,</p>"
        + "<p>Votre image a été convertie en noir et blanc.</p>"
        + "<p>Voici le lien pour la télécharger : "
        + "<a href=\""
        + downloadLink
        + "\">Télécharger l'image</a></p>"
        + "<p><em>Le lien va expirer dans "
        + LINK_EXPIRATION.toDays()
        + " jours.</em></p>";
  }
}
