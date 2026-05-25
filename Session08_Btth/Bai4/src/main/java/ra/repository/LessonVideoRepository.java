package ra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.model.entity.LessonVideo;

public interface LessonVideoRepository extends JpaRepository<LessonVideo, Long> {
}