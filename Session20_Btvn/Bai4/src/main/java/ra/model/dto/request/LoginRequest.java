package ra.model.dto.request;

import lombok.*;
import java.util.List;

@Data
public class LoginRequest {
    private String email;
    private String password;
}