package ra.model.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.model.entity.Role;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    @NotBlank(message = "Không được để trống sđt !")
    private String phone;
    @NotBlank(message = "Không được để trống mật khẩu !")
    private String password;
    @NotBlank(message = "Không được để trống !")
    private String email;
    @NotNull(message = "Không được để trống vai trò !")
    private List<Role> roles;
}
