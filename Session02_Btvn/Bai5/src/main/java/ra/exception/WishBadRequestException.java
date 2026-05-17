package ra.exception;

public class WishBadRequestException extends RuntimeException {
    public WishBadRequestException(String message) {
        super(message);
    }
}
