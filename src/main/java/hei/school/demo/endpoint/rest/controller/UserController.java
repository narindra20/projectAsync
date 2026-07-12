package hei.school.demo.endpoint.rest.controller;

import hei.school.demo.repository.UserRepository;
import hei.school.demo.repository.model.User;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

  private final UserRepository userRepository;

  @PostMapping("/users")
  public User create(@RequestBody CreateUserRequest request) {
    User user =
        User.builder()
            .firstName(request.firstName())
            .lastName(request.lastName())
            .userName(request.userName())
            .email(request.email())
            .build();
    return userRepository.save(user);
  }

  @GetMapping("/users")
  public List<User> getAll() {
    return userRepository.findAll();
  }

  public record CreateUserRequest(
      String firstName, String lastName, String userName, String email) {}
}
