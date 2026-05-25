package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ra.model.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
            "FROM user_courses WHERE user_id = :userId AND course_id = :courseId",
            nativeQuery = true)
    boolean isCoursePurchased(@Param("userId") String userId, @Param("courseId") Long courseId);
}