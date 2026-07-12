package hei.school.demo.service;

import hei.school.demo.endpoint.event.EventProducer;
import hei.school.demo.endpoint.event.model.SubscriptionCreated;
import hei.school.demo.endpoint.rest.controller.dto.SubscriptionRequest;
import hei.school.demo.mail.Email;
import hei.school.demo.mail.Mailer;
import hei.school.demo.mapper.SubscriptionMapper;
import hei.school.demo.model.Subscription;
import hei.school.demo.repository.SubscriptionRepository;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscriptionService {
  private final Mailer mailer;
  private final SubscriptionRepository repository;
  private final SubscriptionMapper mapper;
  private final UserService userService;
  private final EventProducer<SubscriptionCreated> eventProducer;

  public Subscription create(UUID id, SubscriptionRequest subscriptionRequest) {
    var asEntity = mapper.toEntity(id, subscriptionRequest);
    var saved = mapper.toModel(repository.save(asEntity));
    eventProducer.accept(List.of(new SubscriptionCreated(saved)));
    return saved;
  }

  private void sendEmailToUserId(UUID userId) throws AddressException {
    var user = userService.getById(userId);
    var to = user.email();
    var subject = "Subscription confirmation";
    var htmlBody =
        """
        <html>
          <body>
            <p>Dear %s,</p>
            <p>Your subscription has been confirmed. You now have full access to your course.</p>
            <p>Thank you for joining us!</p>
            <p>Best regards,</p>
            <p>The Team</p>
          </body>
        </html>
        """
            .formatted(user.userName());
    var email =
        new Email(new InternetAddress(to), List.of(), List.of(), subject, htmlBody, List.of());
    mailer.accept(email);
  }
}
