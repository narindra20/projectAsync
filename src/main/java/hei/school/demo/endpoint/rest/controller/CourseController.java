package hei.school.demo.endpoint.rest.controller;

import hei.school.demo.endpoint.rest.controller.dto.SubscriptionRequest;
import hei.school.demo.model.Subscription;
import hei.school.demo.service.SubscriptionService;
import jakarta.mail.internet.AddressException;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {
  private final SubscriptionService subscriptionService;

  @PostMapping("/{id}/subscribe")
  public Subscription subscribe(
      @PathVariable UUID id, @RequestBody SubscriptionRequest subscriptionRequest)
      throws AddressException {
    return subscriptionService.create(id, subscriptionRequest);
  }
}
