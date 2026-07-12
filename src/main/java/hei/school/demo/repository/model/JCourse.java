package hei.school.demo.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "course")
public class JCourse {
  @Id @GeneratedValue private UUID id;

  @Column(nullable = false, unique = true)
  private String title;

  private Instant start;
  private Instant end;

  @OneToMany(mappedBy = "course")
  private List<JSubscription> subscriptions;
}
