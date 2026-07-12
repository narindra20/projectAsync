package hei.school.demo.endpoint.rest.controller;

import hei.school.demo.repository.CourseRepository;
import hei.school.demo.repository.model.Course;
import java.time.Instant;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CourseController {

  private final CourseRepository courseRepository;

  @PostMapping("/courses")
  public Course create(@RequestBody CreateCourseRequest request) {
    Course course =
        Course.builder().title(request.title()).start(request.start()).end(request.end()).build();
    return courseRepository.save(course);
  }

  public record CreateCourseRequest(String title, Instant start, Instant end) {}
}
