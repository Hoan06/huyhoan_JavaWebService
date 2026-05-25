package ra.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, String>> handleBusinessException(BusinessException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error_type", "BUSINESS_LOGIC_ERROR");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleSystemException(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error_type", "SYSTEM_CRITICAL_ERROR");
        response.put("message", "Hệ thống đang gặp sự cố gián đoạn kỹ thuật. Vui lòng thử lại sau!");

        log.error("[GLOBAL ERROR INTERCEPTOR] Chặn đứng ngoại lệ RuntimeException chưa được xử lý: {}", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}