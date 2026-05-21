package ra;

import org.springframework.stereotype.Service;

@Service
public class CourseService {
    public Object getCourseDetails(Long courseId) {
        if (courseId == 999) {
            throw new ResourceNotFoundException("Không tìm thấy khóa học với ID: " + courseId);
        }
        return "Thông tin khóa học Java nâng cao";
    }
}