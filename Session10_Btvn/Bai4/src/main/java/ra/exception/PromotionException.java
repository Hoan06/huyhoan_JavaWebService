package ra.exception;

import lombok.Getter;

@Getter
public class PromotionException extends RuntimeException {
    private final String errorCode;

    public PromotionException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
