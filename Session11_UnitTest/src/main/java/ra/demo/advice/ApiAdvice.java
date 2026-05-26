package ra.demo.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.demo.exception.CategoryNotFoundException;
import ra.demo.model.dto.response.ApiDataResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApiAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiDataResponse<Map<String, String>>> handleMethodArgument(MethodArgumentNotValidException me) {
        Map<String, String> map = new HashMap<>();
        for (FieldError fieldError : me.getFieldErrors()) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        log.error("{} - {} - {}","Nguyễn Thanh Bình","INSERT",map);
        return new ResponseEntity<>(new ApiDataResponse<>(
                false,
                "Lỗi xác thực dữ liệu",
                null,
                map,
                HttpStatus.BAD_REQUEST
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiDataResponse<String>> handleHttpMessage(HttpMessageNotReadableException me) {
        log.error("{} - {} - {}","Nguyễn Thanh Bình","INSERT",me.getLocalizedMessage());
        return new ResponseEntity<>(new ApiDataResponse<>(
                false,
                "Lỗi xác thực dữ liệu",
                null,
                "Sai kiểu định dạng dữ liệu",
                HttpStatus.BAD_REQUEST
        ), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiDataResponse<String>> handleCategoryNotFound(CategoryNotFoundException ex) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                false,
                "Có lỗi xảy ra",
                null,
                ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST
        ), HttpStatus.BAD_REQUEST);
    }
}
