package ra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ra.model.dto.response.ApiDataResponse;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandle {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiDataResponse<Map<String,String>>> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiDataResponse<Map<String,String>> apiDataResponse = new ApiDataResponse<>(
                true,
                "Dữ liệu không hợp lệ",
                null,
                errors,
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(apiDataResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UniqueEmailException.class)
    public ResponseEntity<ApiDataResponse<String>> uniqueEmailException(UniqueEmailException ex) {
        ApiDataResponse<String> response = new ApiDataResponse<>(
                false,
                ex.getMessage(),
                null,
                ex.getMessage(),
                HttpStatus.CONFLICT
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
