package hei.school.demo.repository.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
  @Id private String id;
  private String firstName;
  private String lastName;
  private String userName;
  private String email;
}
