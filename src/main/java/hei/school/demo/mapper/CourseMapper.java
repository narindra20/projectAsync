package hei.school.demo.mapper;

import hei.school.demo.model.Course;
import hei.school.demo.repository.model.JCourse;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
  public Course toModel(JCourse entity) {
    return Course.builder()
        .id(entity.getId())
        .title(entity.getTitle())
        .start(entity.getStart())
        .end(entity.getEnd())
        .build();
  }

  public List<Course> toModel(List<JCourse> entities) {
    return entities.stream().map(this::toModel).toList();
  }

  public JCourse toEntity(Course model) {
    return JCourse.builder()
        .id(model.id())
        .title(model.title())
        .start(model.start())
        .end(model.end())
        .build();
  }

  public List<JCourse> toEntity(List<Course> models) {
    return models.stream().map(this::toEntity).toList();
  }
}
