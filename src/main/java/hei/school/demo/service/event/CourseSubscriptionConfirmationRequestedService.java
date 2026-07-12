package hei.school.demo.service.event;

import static java.io.File.createTempFile;

import hei.school.demo.endpoint.event.model.CourseSubscriptionConfirmationRequested;
import hei.school.demo.file.bucket.BucketComponent;
import hei.school.demo.mail.Email;
import hei.school.demo.mail.Mailer;
import hei.school.demo.ticket.TicketPdfGenerator;
import jakarta.mail.internet.InternetAddress;
import java.io.File;
import java.io.FileOutputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CourseSubscriptionConfirmationRequestedService
    implements Consumer<CourseSubscriptionConfirmationRequested> {

  private final Mailer mailer;
  private final BucketComponent bucketComponent;
  private final TicketPdfGenerator ticketPdfGenerator;

  @SneakyThrows
  @Override
  public void accept(CourseSubscriptionConfirmationRequested event) {
    var recipientAddress = new InternetAddress(event.getUserEmail());

    byte[] pdfBytes =
        ticketPdfGenerator.generate(event.getUserEmail(), event.getCourseTitle(), Instant.now());

    String bucketKey = "tickets/" + event.getUserId() + "-" + event.getCourseId() + ".pdf";
    File tempFile = createTempFile("ticket-", ".pdf");
    try (FileOutputStream fos = new FileOutputStream(tempFile)) {
      fos.write(pdfBytes);
    }
    bucketComponent.upload(tempFile, bucketKey);

    String downloadLink = bucketComponent.presign(bucketKey, Duration.ofDays(7)).toString();

    mailer.accept(
        new Email(
            recipientAddress,
            List.of(),
            List.of(),
            "Confirmation d'inscription",
            "<p>Bonjour,</p>"
                + "<p>Vous êtes bien inscrit au cours : <strong>"
                + event.getCourseTitle()
                + "</strong>.</p>"
                + "<p>Voici le lien pour télécharger votre ticket : "
                + "<a href=\""
                + downloadLink
                + "\">Télécharger le ticket</a></p>",
            List.of()));

    log.info("Ticket uploaded and email sent for user {}", event.getUserId());
  }
}
