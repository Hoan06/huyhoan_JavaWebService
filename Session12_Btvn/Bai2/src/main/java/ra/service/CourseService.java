package ra.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ra.model.entity.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private static final Logger log = LoggerFactory.getLogger(CourseService.class);
    private final List<Course> courseList = new ArrayList<>();
    private long currentId = 1;

    public CourseService() {
        courseList.add(new Course(currentId++, "Java Web Spring Boot", "Nguyen Huy Hoan", 40, 500.0));
        courseList.add(new Course(currentId++, "React JS Modern", "Alice", 30, 350.0));
    }

    public List<Course> getAllCourses() {
        return courseList;
    }

    public Optional<Course> getCourseById(Long id) {
        Optional<Course> course = courseList.stream().filter(c -> c.getId().equals(id)).findFirst();
        if (course.isEmpty()) {
            log.warn("Course Service: Không tìm thấy khóa học với ID = {}", id);
        }
        return course;
    }

    public Course createCourse(Course course) {
        course.setId(currentId++);
        courseList.add(course);
        log.info("Course Service: Tạo mới khóa học thành công - ID: {}, Name: {}", course.getId(), course.getCourseName());
        return course;
    }

    public Optional<Course> updateCourse(Long id, Course courseDetails) {
        Optional<Course> optionalCourse = getCourseById(id);
        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();
            existingCourse.setCourseName(courseDetails.getCourseName());
            existingCourse.setInstructor(courseDetails.getInstructor());
            existingCourse.setDurationHours(courseDetails.getDurationHours());
            existingCourse.setFee(courseDetails.getFee());
            log.info("Course Service: Cập nhật khóa học thành công - ID: {}", id);
            return Optional.of(existingCourse);
        }
        return Optional.empty();
    }

    public boolean deleteCourse(Long id) {
        Optional<Course> optionalCourse = getCourseById(id);
        if (optionalCourse.isPresent()) {
            courseList.remove(optionalCourse.get());
            return true;
        }
        return false;
    }
}