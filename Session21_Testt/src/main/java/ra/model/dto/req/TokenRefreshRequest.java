package ra.model.dto.req;
import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}