package ra.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(Map.of(
                "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "message", "Quá trình thanh toán / đồng bộ kho gặp sự cố kỹ thuật. Vui lòng thử lại sau!"
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}