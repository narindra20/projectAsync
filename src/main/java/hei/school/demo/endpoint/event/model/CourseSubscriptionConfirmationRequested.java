package hei.school.demo.endpoint.event.model;

import java.time.Duration;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class CourseSubscriptionConfirmationRequested extends PojaEvent {
  private UUID userId;
  private UUID courseId;
  private String userEmail;
  private String courseTitle;

  @Override
  public Duration maxConsumerDuration() {
    return Duration.ofSeconds(20);
  }

  @Override
  public Duration maxConsumerBackoffBetweenRetries() {
    return Duration.ofMinutes(1);
  }
}
