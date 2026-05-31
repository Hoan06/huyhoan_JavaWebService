package ra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ra.model.dto.response.ApiDataResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiDataResponse<Map<String,String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ApiDataResponse<Map<String,String>> apiDataResponse = new ApiDataResponse<>(
                false,
                "Dữ liệu gửi lên không hợp lệ !",
                null,
                errors,
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(apiDataResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiDataResponse<String>> handleNoSuchElementException(NoSuchElementException ex) {

        ApiDataResponse<String> apiDataResponse = new ApiDataResponse<>(
                false,
                "Tài nguyên không tồn tại !",
                null,
                ex.getMessage(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(apiDataResponse, HttpStatus.NOT_FOUND);
    }
}
