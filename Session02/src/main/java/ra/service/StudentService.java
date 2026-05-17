package ra.service;

import ra.model.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> getStudents();
    Student getStudentById(Long id);
    Student insertStudent(Student student);
    Student updateStudent(Long stuId , Student student);
    boolean deleteStudent(Long stuId);
    List<Student> getStudentsByName(String name);
}
