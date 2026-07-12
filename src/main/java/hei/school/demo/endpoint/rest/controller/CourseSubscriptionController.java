package hei.school.demo.endpoint.rest.controller;

import hei.school.demo.endpoint.event.EventProducer;
import hei.school.demo.endpoint.event.model.CourseSubscriptionConfirmationRequested;
import hei.school.demo.repository.CourseRepository;
import hei.school.demo.repository.UserRepository;
import hei.school.demo.repository.model.Course;
import hei.school.demo.repository.model.User;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CourseSubscriptionController {

  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final EventProducer<CourseSubscriptionConfirmationRequested> eventProducer;

  @PostMapping("/courses/{courseId}/subscribe")
  @Transactional
  public ResponseEntity<Void> subscribe(
      @PathVariable UUID courseId, @RequestBody SubscribeToCourseRequest request) {

    User user =
        userRepository
            .findById(request.userId())
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
    Course course =
        courseRepository
            .findById(courseId)
            .orElseThrow(() -> new EntityNotFoundException("Course not found"));

    if (user.getCourses().contains(course)) {
      throw new IllegalStateException("User already subscribed to this course");
    }

    user.getCourses().add(course);
    userRepository.save(user);

    eventProducer.accept(
        List.of(
            CourseSubscriptionConfirmationRequested.builder()
                .userId(user.getId())
                .courseId(course.getId())
                .userEmail(user.getEmail())
                .courseTitle(course.getTitle())
                .build()));

    return ResponseEntity.ok().build();
  }

  public record SubscribeToCourseRequest(UUID userId) {}
}
