package ra.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ra.custom_valid.MedicationStatusValid;
import ra.model.entity.MedicationStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MedicationDTO {
    @NotBlank(message = "Tên thuốc không được để trống !")
    private String name;
    @NotBlank(message = "Tên nhà sản xuất không được để trống !")
    private String manufacturer;
    @NotNull(message = "Giá thuốc không được để trống !")
    @Min(value = 0 , message = "Giá thuốc phải lớn hơn 0 !")
    private Double price;
    @NotNull(message = "Không được để trống trạng thái !")
    @MedicationStatusValid
    private MedicationStatus status;
    private Boolean delete;
}
