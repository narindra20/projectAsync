package hei.school.demo.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record Course(
    UUID id, String title, Instant start, Instant end, List<Subscription> subscriptions) {}
