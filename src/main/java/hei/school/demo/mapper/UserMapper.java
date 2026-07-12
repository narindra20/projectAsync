package hei.school.demo.mapper;

import hei.school.demo.model.User;
import hei.school.demo.repository.model.JUser;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  public User toModel(JUser entity) {
    return User.builder()
        .id(entity.getId())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .userName(entity.getUserName())
        .email(entity.getEmail())
        .build();
  }

  public List<User> toModel(List<JUser> entities) {
    return entities.stream().map(this::toModel).toList();
  }

  public JUser toEntity(User model) {
    return JUser.builder()
        .id(model.id())
        .firstName(model.firstName())
        .lastName(model.lastName())
        .userName(model.userName())
        .email(model.email())
        .build();
  }
}
