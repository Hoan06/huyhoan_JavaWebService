package sesison15.ra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sesison15.ra.model.entity.Student;
import sesison15.ra.repository.StudentRepository;
import sesison15.ra.service.StudentService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public List<Student> getAlls() {
        return studentRepository.findAll();
    }

    @Override
    public Student insert(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student update(Long stuId , Student student) {
        studentRepository.findById(stuId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy sinh viên : " + stuId));
        student.setStuId(stuId);
        return studentRepository.save(student);
    }

    @Override
    public Student updatePatch(Long stuId, Student student) {
        Student stu = studentRepository.findById(stuId).orElseThrow(() -> new NoSuchElementException("Không tìm thấy sinh viên : " + stuId));
        if (student.getFullName() != null && student.getFullName().length() > 0) {
            stu.setFullName(student.getFullName());
        }
        if (student.getEmail() != null && student.getEmail().length() > 0) {
            stu.setEmail(student.getEmail());
        }
        if (student.getGpa() != null && student.getGpa() > 0){
            stu.setGpa(student.getGpa());
        }
        return studentRepository.save(stu);
    }

    @Override
    public Boolean deleteById(Long id) {
        studentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Không tìm thấy sinh viên : " + id));
        studentRepository.deleteById(id);
        return true;
    }

    @Override
    public Student findById(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        return student;
    }
}
