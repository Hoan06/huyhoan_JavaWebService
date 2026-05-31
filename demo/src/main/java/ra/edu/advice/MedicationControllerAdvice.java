package ra.edu.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.edu.exception.MedicationExist;
import ra.edu.exception.MedicationNotFound;
import ra.edu.model.dto.response.ApiDataResponse;

import java.util.Map;
import java.util.TreeMap;

@RestControllerAdvice
@Slf4j
public class MedicationControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiDataResponse<Map<String, String>>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new TreeMap<>();
        for (FieldError fieldError : ex.getFieldErrors()) {
            erros.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.warn("Lỗi: {}", erros);
        return new ResponseEntity<>(new ApiDataResponse<>(
                false,
                "Lỗi xác thực dữ liệu",
                null,
                erros,
                HttpStatus.BAD_REQUEST
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MedicationExist.class)
    public ResponseEntity<ApiDataResponse<String>> handleMedicationExist(MedicationExist ex) {
        log.warn("Lỗi: {}", ex.getLocalizedMessage());
        return new ResponseEntity<>(new ApiDataResponse<>(
                false,
                "Lỗi: Trùng lặp tên thuốc",
                null,
                ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MedicationNotFound.class)
    public ResponseEntity<ApiDataResponse<String>> handleMedicationNotFound(MedicationNotFound ex) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                false,
                "Lỗi: Không tìm thấy tên thuốc",
                null,
                ex.getLocalizedMessage(),
                HttpStatus.NOT_FOUND
        ), HttpStatus.NOT_FOUND);
    }
}
