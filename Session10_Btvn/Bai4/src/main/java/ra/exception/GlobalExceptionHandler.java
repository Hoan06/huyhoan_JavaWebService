package ra.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ra.model.dto.response.ErrorResponseDTO;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(PromotionException.class)
    public ResponseEntity<ErrorResponseDTO> handlePromotionException(PromotionException ex) {
        log.warn("[GLOBAL HANDLER - WARN] Vi phạm quy tắc nghiệp vụ hệ thống Khuyến mãi: {}", ex.getMessage());

        ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String defaultMessage = (fieldError != null) ? fieldError.getDefaultMessage() : "Dữ liệu đầu vào không hợp lệ";

        log.warn("[GLOBAL HANDLER - WARN] Dữ liệu gửi lên sai định dạng Validation: {}", defaultMessage);

        ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errorCode("PROMO_INVALID")
                .message(defaultMessage)
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleSystemException(Exception ex) {
        log.error("[GLOBAL HANDLER - CRITICAL ERROR] Phát hiện sự cố hệ thống nghiêm trọng tại phân hệ Khuyến mãi: ", ex);

        ErrorResponseDTO errorDTO = ErrorResponseDTO.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Lỗi máy chủ nội bộ. Vui lòng liên hệ quản trị viên")
                .build();

        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}