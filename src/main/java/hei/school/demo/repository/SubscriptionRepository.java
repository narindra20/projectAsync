package hei.school.demo.repository;

import hei.school.demo.repository.model.JSubscription;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<JSubscription, UUID> {}
