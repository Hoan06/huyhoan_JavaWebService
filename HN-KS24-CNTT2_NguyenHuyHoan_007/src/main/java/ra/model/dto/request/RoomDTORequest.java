package ra.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.model.entity.RoomStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomDTORequest {
    @NotNull(message = "So phong khong duoc de trong !")
    @Min(value = 1, message = "So phong phai lon hon 0 !")
    private Integer roomNumber;

    @NotBlank(message = "Loai phong khong duoc de trong !")
    private String roomType;

    @NotNull(message = "Gia phong khong duoc de trong !")
    @DecimalMin(value = "0.0", inclusive = false, message = "Gia phong phai lon hon 0 !")
    private Double pricePerNight;

    @NotNull(message = "Khong duoc de trong trang thai !")
    private RoomStatus status;

    private Boolean isDeleted;
}
