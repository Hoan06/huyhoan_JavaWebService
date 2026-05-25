package ra.service;

import org.springframework.stereotype.Service;
import ra.exception.AccessDeniedException;
import ra.exception.ResourceNotFoundException;
import ra.model.entity.LessonVideo;
import ra.repository.CourseRepository;
import ra.repository.LessonVideoRepository;

@Service
public class LessonService {

    private final LessonVideoRepository lessonVideoRepository;
    private final CourseRepository courseRepository;

    public LessonService(LessonVideoRepository lessonVideoRepository, CourseRepository courseRepository) {
        this.lessonVideoRepository = lessonVideoRepository;
        this.courseRepository = courseRepository;
    }

    public String watchLesson(Long lessonId, String userId) {
        LessonVideo video = lessonVideoRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Bài giảng video không tồn tại trên hệ thống."));

        if (!video.getIsFree()) {
            if (userId == null || userId.isBlank()) {
                throw new AccessDeniedException("Vui lòng đăng nhập để truy cập bài học này.");
            }
            boolean hasPurchased = courseRepository.isCoursePurchased(userId, video.getCourseId());
            if (!hasPurchased) {
                throw new AccessDeniedException("You have not purchased this course yet.");
            }
        }

        return video.getVideoUrl();
    }
}