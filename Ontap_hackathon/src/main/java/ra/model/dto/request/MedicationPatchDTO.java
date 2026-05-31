package ra.model.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;
import ra.custom_valid.MedicationStatusValid;
import ra.model.entity.MedicationStatus;

@Data
public class MedicationPatchDTO {
    private String name;
    private String manufacturer;

    @Min(value = 0, message = "Giá thuốc phải lớn hơn hoặc bằng 0 nếu cập nhật!")
    private Double price;

    @MedicationStatusValid
    private MedicationStatus status;
}