package hei.school.demo.service.event;

import hei.school.demo.endpoint.event.model.CourseSubscriptionConfirmationRequested;
import hei.school.demo.mail.Email;
import hei.school.demo.mail.Mailer;
import jakarta.mail.internet.InternetAddress;
import java.util.List;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CourseSubscriptionConfirmationRequestedService
    implements Consumer<CourseSubscriptionConfirmationRequested> {

  private final Mailer mailer;

  @SneakyThrows
  @Override
  public void accept(CourseSubscriptionConfirmationRequested event) {
    var recipientAddress = new InternetAddress(event.getUserEmail());
    mailer.accept(
        new Email(
            recipientAddress,
            List.of(),
            List.of(),
            "Confirmation d'inscription",
            "Vous êtes bien inscrit au cours : " + event.getCourseTitle(),
            List.of()));
  }
}
