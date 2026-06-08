package ra.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProgressResponse {
    private Integer totalLessonsCompleted;
    private java.util.List<String> graduatedCourses;
}