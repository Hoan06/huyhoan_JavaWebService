package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}