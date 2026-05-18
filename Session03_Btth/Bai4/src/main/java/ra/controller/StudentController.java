package ra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.Bai4Application;
import ra.model.dto.response.ApiDataResponse;
import ra.model.entity.Student;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    public StudentController() {
        students.add(new Student("SV001", "Nguyen Van A", 8.5));
        students.add(new Student("SV002", "Tran Thi B", 7.8));
        students.add(new Student("SV003", "Le Van C", 9.2));
        students.add(new Student("SV004", "Pham Thi D", 6.9));
    }

    @GetMapping(
            value = "/{id}",
            produces = { "application/json", "application/xml" }
    )
    public ResponseEntity<ApiDataResponse<List<Student>>> getAllStudents() {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy sinh viên thành công",
                students,
                HttpStatus.OK
        ), HttpStatus.OK);
    }
}
