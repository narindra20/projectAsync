package hei.school.demo.service;

import hei.school.demo.mapper.CourseMapper;
import hei.school.demo.model.Course;
import hei.school.demo.repository.CourseRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseService {
  private final CourseMapper mapper;
  private final CourseRepository repository;

  public Course getById(UUID id) {
    return mapper.toModel(
        repository.findById(id).orElseThrow(() -> new RuntimeException("Course not found")));
  }
}
