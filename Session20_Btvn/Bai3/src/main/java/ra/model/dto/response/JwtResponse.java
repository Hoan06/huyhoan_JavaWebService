package ra.model.dto.response;

import lombok.*;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
}