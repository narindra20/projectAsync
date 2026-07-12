package hei.school.demo.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course {
  @Id private String id;
  private String title;
  private Instant start;
  private Instant end;
}
