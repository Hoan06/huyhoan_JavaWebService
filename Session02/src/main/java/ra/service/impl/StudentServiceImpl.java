package ra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ra.model.entity.Student;
import ra.repository.StudentRepository;
import ra.service.StudentService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Student with id " + id + " does not exist"));
    }

    @Override
    public Student insertStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long stuId, Student student) {
        studentRepository.findById(stuId).orElseThrow(() -> new NoSuchElementException("Student with id " + stuId + " does not exist"));
        student.setStuId(stuId);
        return studentRepository.save(student);
    }

    @Override
    public boolean deleteStudent(Long stuId) {
        studentRepository.findById(stuId).orElseThrow(() -> new NoSuchElementException("Student with id " + stuId + " does not exist"));
        studentRepository.deleteById(stuId);
        return true;
    }

    @Override
    public List<Student> getStudentsByName(String name) {
        return studentRepository.getStudentsByFullNameContaining(name);
    }
}
