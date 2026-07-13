package hei.school.demo.endpoint.rest.controller;

import hei.school.demo.endpoint.event.EventProducer;
import hei.school.demo.endpoint.event.model.ImageUploadedRequested;
import hei.school.demo.file.bucket.BucketComponent;
import hei.school.demo.repository.ImageSubmissionRepository;
import hei.school.demo.repository.model.ImageSubmission;
import java.io.File;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
public class ImageController {

  private static final Set<String> ALLOWED_TYPES = Set.of("image/jpeg", "image/png");

  private final ImageSubmissionRepository imageSubmissionRepository;
  private final BucketComponent bucketComponent;
  private final EventProducer<ImageUploadedRequested> eventProducer;

  @PostMapping("/images")
  @ResponseStatus(HttpStatus.CREATED)
  @SneakyThrows
  public ImageSubmission submit(
      @RequestParam("file") MultipartFile file, @RequestParam("email") String email) {

    if (!ALLOWED_TYPES.contains(file.getContentType())) {
      throw new IllegalArgumentException("Only JPEG and PNG images are allowed");
    }

    ImageSubmission submission =
        ImageSubmission.builder().fileName(file.getOriginalFilename()).email(email).build();
    submission = imageSubmissionRepository.save(submission);

    String originalKey = "originals/" + submission.getId() + "-" + file.getOriginalFilename();
    File tempFile = File.createTempFile("upload-", file.getOriginalFilename());
    file.transferTo(tempFile);
    bucketComponent.upload(tempFile, originalKey);

    eventProducer.accept(
        List.of(
            ImageUploadedRequested.builder()
                .imageId(submission.getId())
                .originalBucketKey(originalKey)
                .email(email)
                .build()));

    return submission;
  }

  @GetMapping("/images")
  public List<ImageSubmission> getAll() {
    return imageSubmissionRepository.findAll();
  }
}
