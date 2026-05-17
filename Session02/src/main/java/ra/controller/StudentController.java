package ra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.response.ApiDataResponse;
import ra.model.entity.Student;
import ra.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<Student>>> getAllStudents() {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy sinh viên thành công",
                studentService.getStudents(),
                HttpStatus.OK
        ) , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<Student>> addStudent(@RequestBody Student student) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Thêm mới sinh viên thành công",
                studentService.insertStudent(student),
                HttpStatus.CREATED
        ) , HttpStatus.CREATED);
    }

    @PutMapping("/{stuId}")
    public ResponseEntity<ApiDataResponse<Student>> updateStudent(@RequestBody Student student , @PathVariable Long stuId) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật sinh viên " + stuId + " thành công",
                studentService.updateStudent(stuId,student),
                HttpStatus.OK
        ) , HttpStatus.OK);
    }

    @DeleteMapping("/{stuId}")
    public ResponseEntity<ApiDataResponse<Boolean>> deleteStudent(@PathVariable Long stuId) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Xóa sinh viên " + stuId + " thành công",
                studentService.deleteStudent(stuId),
                HttpStatus.NO_CONTENT
        ) , HttpStatus.NO_CONTENT);
    }

    @GetMapping("/student-by-name/{fullName}")
    public ResponseEntity<ApiDataResponse<List<Student>>> getStudentByFullName(@PathVariable String fullName) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy sinh viên thành công",
                studentService.getStudentsByName(fullName),
                HttpStatus.OK
        ) , HttpStatus.OK);
    }

    @GetMapping("/{stuId}")
    public ResponseEntity<ApiDataResponse<Student>> getStudentById(@PathVariable Long stuId) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy thông tin sinh viên thành công",
                studentService.getStudentById(stuId),
                HttpStatus.OK
        ) , HttpStatus.OK);
    }

}
