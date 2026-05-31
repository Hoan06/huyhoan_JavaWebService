package ra.service;

import org.springframework.stereotype.Service;
import ra.exception.StudentNotFoundException;
import ra.model.entity.Student;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final List<Student> studentList = new ArrayList<>();
    private long currentId = 1;

    public StudentService() {
        studentList.add(new Student(currentId++, "SV001", "Nguyen Huy Hoan", "Information Technology", 3.8));
        studentList.add(new Student(currentId++, "SV002", "Alice Smith", "Data Science", 3.5));
    }

    public List<Student> getAllStudents() {
        return studentList;
    }

    public Student getStudentById(Long id) {
        return studentList.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException("Không tìm thấy sinh viên với ID: " + id));
    }

    public Student createStudent(Student student) {
        student.setId(currentId++);
        studentList.add(student);
        return student;
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Student existingStudent = getStudentById(id);
        existingStudent.setStudentCode(studentDetails.getStudentCode());
        existingStudent.setFullName(studentDetails.getFullName());
        existingStudent.setMajor(studentDetails.getMajor());
        existingStudent.setGpa(studentDetails.getGpa());
        return existingStudent;
    }

    public boolean deleteStudent(Long id) {
        Student student = getStudentById(id);
        return studentList.remove(student);
    }
}