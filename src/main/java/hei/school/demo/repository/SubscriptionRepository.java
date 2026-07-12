package hei.school.demo.repository;

import hei.school.demo.repository.model.Subscription;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {

  Optional<Subscription> findByUser_IdAndCourse_Id(String userId, String courseId);
}
