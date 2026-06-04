package ra.demo.model.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.demo.model.entity.Role;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    @NotBlank(message = "Không được để trống username")
    private String username;
    @NotBlank(message = "Không được để trống password")
    private String password;
    @NotBlank(message = "Không được để trống full name")
    private String fullName;
    @NotBlank(message = "Không được để trống email")
    private String email;
    @NotBlank(message = "Không được để trống phone")
    private String phone;
    @NotNull(message = "Không được để trống role")
    private List<Role> roles;
}
