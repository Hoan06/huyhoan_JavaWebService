package sesison15.ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sesison15.ra.model.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
