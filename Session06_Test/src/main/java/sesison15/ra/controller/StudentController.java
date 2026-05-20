package sesison15.ra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sesison15.ra.model.dto.response.ApiDataResponse;
import sesison15.ra.model.entity.Student;
import sesison15.ra.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<Student>>> getAllStudents() {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách sinh viên thành công.",
                studentService.getAlls(),
                HttpStatus.OK
        ) , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<Student>> addStudent(@RequestBody Student student) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Thêm mới sinh viên thành công.",
                studentService.insert(student),
                HttpStatus.CREATED
        ) , HttpStatus.CREATED);
    }

    @PutMapping("/{stuId}")
    public ResponseEntity<ApiDataResponse<Student>> updateStudent(@PathVariable Long stuId, @RequestBody Student student) {
        Student studentCheck = studentService.findById(stuId);
        if (studentCheck == null) {
            return new ResponseEntity<>(new ApiDataResponse<>(
                    false,
                    "Không tìm thấy sinh viên với ID: " + stuId,
                    null,
                    HttpStatus.NOT_FOUND
            ) , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật sinh viên thành công .",
                studentService.update(stuId,student),
                HttpStatus.OK
        ) , HttpStatus.OK);
    }

    @PatchMapping("/{stuId}")
    public ResponseEntity<ApiDataResponse<Student>> patchStudent(@PathVariable Long stuId, @RequestBody Student student) {
        Student studentCheck = studentService.findById(stuId);
        if (studentCheck == null) {
            return new ResponseEntity<>(new ApiDataResponse<>(
                    false,
                    "Không tìm thấy sinh viên với ID: " + stuId,
                    null,
                    HttpStatus.NOT_FOUND
            ) , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật sinh viên thành công .",
                studentService.updatePatch(stuId,student),
                HttpStatus.OK
        ) , HttpStatus.OK);
    }

    @DeleteMapping("/{stuId}")
    public ResponseEntity<ApiDataResponse<Boolean>> deleteStudent(@PathVariable Long stuId) {
        Student student = studentService.findById(stuId);
        if (student == null) {
            return new ResponseEntity<>(new ApiDataResponse<>(
                    false,
                    "Không tìm thấy sinh viên với ID: " + stuId,
                    null,
                    HttpStatus.NOT_FOUND
            ) , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Xóa sinh viên thành công.",
                studentService.deleteById(stuId),
                HttpStatus.NO_CONTENT
        ) , HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{stuId}")
    public ResponseEntity<ApiDataResponse<Student>> getStudent(@PathVariable Long stuId) {
        Student student = studentService.findById(stuId);
        if (student == null) {
            return new ResponseEntity<>(new ApiDataResponse<>(
                    false,
                    "Không tìm thấy sinh viên với ID: " + stuId,
                    null,
                    HttpStatus.NOT_FOUND
            ) , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy sinh viên chi tiết thành công .",
                studentService.findById(stuId),
                HttpStatus.OK
        ) , HttpStatus.OK);
    }
}
