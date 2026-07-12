package hei.school.demo.service;

import hei.school.demo.mapper.UserMapper;
import hei.school.demo.model.User;
import hei.school.demo.repository.UserRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private final UserMapper mapper;
  private final UserRepository repository;

  public User getById(UUID id) {
    return mapper.toModel(
        repository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
  }
}
