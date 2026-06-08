package ra.service;

import ra.controller.CertificateClient;
import ra.exception.CourseNotCompletedException;
import ra.model.dto.request.CertificatePartnerRequest;
import ra.model.dto.response.CertificatePartnerResponse;
import ra.model.dto.response.ProgressResponse;
import ra.model.entity.Course;
import ra.model.entity.Enrollment;
import ra.model.entity.Student;
import ra.repository.CourseRepository;
import ra.repository.EnrollmentRepository;
import ra.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final CertificateClient certificateClient;

    public ProgressResponse getMyProgress(String email) {
        Student student = studentRepository.findByEmail(email).get();
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(student.getId());

        int totalLessonsCompleted = enrollments.stream()
                .mapToInt(Enrollment::getLessonsCompleted)
                .sum();

        List<String> graduatedCourses = enrollments.stream()
                .filter(en -> {
                    Course course = courseRepository.findById(en.getCourseId()).get();
                    return en.getLessonsCompleted().equals(course.getTotalLessons());
                })
                .map(en -> courseRepository.findById(en.getCourseId()).get().getTitle())
                .collect(Collectors.toList());

        return new ProgressResponse(totalLessonsCompleted, graduatedCourses);
    }

    public CertificatePartnerResponse claimCertificate(Long courseId, String email) {
        Student student = studentRepository.findByEmail(email).get();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Khóa học không tồn tại"));

        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(student.getId());

        Enrollment currentEnrollment = enrollments.stream()
                .filter(en -> en.getCourseId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Bạn chưa ghi danh khóa học này"));

        if (!currentEnrollment.getLessonsCompleted().equals(course.getTotalLessons())) {
            throw new CourseNotCompletedException("Bạn chưa hoàn thành 100% khóa học: " + course.getTitle());
        }

        CertificatePartnerRequest partnerRequest = new CertificatePartnerRequest();
        partnerRequest.setStudentName(student.getFullName());
        partnerRequest.setCourseTitle(course.getTitle());

        return certificateClient.issueCertificate(partnerRequest);
    }
}