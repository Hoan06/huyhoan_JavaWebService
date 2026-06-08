package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Enrollment;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentId(Long studentId);
}