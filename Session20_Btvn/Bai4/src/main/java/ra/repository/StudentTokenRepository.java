package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.StudentToken;

import java.util.List;
import java.util.Optional;

public interface StudentTokenRepository extends JpaRepository<StudentToken, Long> {
    Optional<StudentToken> findByTokenString(String token);
    List<StudentToken> findByStudentIdAndIsRevokedFalse(Long studentId);
}