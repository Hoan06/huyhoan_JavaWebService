package ra.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventoryRequestDTO {
    @NotBlank(message = "Mã SKU không được để trống")
    private String sku;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng thao tác phải lớn hơn 0")
    private Long quantity;

    @NotNull(message = "Mã nhân viên kho không được để trống")
    private Long keeperId;
}