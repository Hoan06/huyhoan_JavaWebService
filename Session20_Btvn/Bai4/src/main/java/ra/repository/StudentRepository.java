package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
}