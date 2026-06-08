package ra.data;

import ra.model.entity.Course;
import ra.model.entity.Enrollment;
import ra.model.entity.Student;
import ra.repository.CourseRepository;
import ra.repository.EnrollmentRepository;
import ra.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@EnableFeignClients(basePackages = "ra")
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Student student = Student.builder()
                .email("hoan@gmail.com")
                .password(passwordEncoder.encode("password123"))
                .fullName("Nguyễn Huy Hoàn")
                .isActive(true)
                .build();
        studentRepository.save(student);

        Course course1 = Course.builder().title("Java Web với Spring Boot").totalLessons(20).build();
        Course course2 = Course.builder().title("Cấu trúc dữ liệu và giải thuật").totalLessons(15).build();
        courseRepository.save(course1);
        courseRepository.save(course2);

        enrollmentRepository.save(Enrollment.builder().studentId(student.getId()).courseId(course1.getId()).lessonsCompleted(20).enrolledDate(LocalDate.now()).build());
        enrollmentRepository.save(Enrollment.builder().studentId(student.getId()).courseId(course2.getId()).lessonsCompleted(5).enrolledDate(LocalDate.now()).build());
    }
}