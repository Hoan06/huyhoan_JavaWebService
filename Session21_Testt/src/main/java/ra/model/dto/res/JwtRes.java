package ra.model.dto.res;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtRes {
    private String accessToken;
    private String refreshToken;
    private String fullName;
    private String role;
}