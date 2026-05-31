package ra.model.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.model.entity.RoomStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomDTORequestUpdate {
    @Min(value = 1, message = "So phong phai lon hon 0 !")
    private Integer roomNumber;

    @Pattern(regexp = ".*\\S.*", message = "Loai phong khong duoc de trong !")
    private String roomType;

    @DecimalMin(value = "0.0", inclusive = false, message = "Gia phong phai lon hon 0 !")
    private Double pricePerNight;

    private RoomStatus status;

    private Boolean isDeleted;
}
