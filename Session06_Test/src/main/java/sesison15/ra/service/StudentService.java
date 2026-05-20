package sesison15.ra.service;

import sesison15.ra.model.entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> getAlls();
    Student insert(Student student);
    Student update(Long stuId , Student student);
    Student updatePatch(Long stuId , Student student);
    Boolean deleteById(Long id);
    Student findById(Long id);
}
