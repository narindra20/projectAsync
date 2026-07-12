package hei.school.demo.endpoint.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Duration;
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

  @JsonProperty("userId")
  private String userId;

  @JsonProperty("courseId")
  private String courseId;

  @JsonProperty("userEmail")
  private String userEmail;

  @JsonProperty("courseTitle")
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
