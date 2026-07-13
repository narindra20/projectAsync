package hei.school.demo.repository;

import hei.school.demo.repository.model.ImageSubmission;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageSubmissionRepository extends JpaRepository<ImageSubmission, UUID> {}
