package ra.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PromotionRequestDTO {
    @NotBlank(message = "Mã khuyến mãi không được để trống")
    private String code;

    @NotNull(message = "Phần trăm giảm giá không được để trống")
    @Min(value = 1, message = "Phần trăm giảm giá tối thiểu là 1%")
    @Max(value = 100, message = "Phần trăm giảm giá tối đa là 100%")
    private Integer discountPercent;

    @NotNull(message = "Trạng thái hoạt động không được để trống")
    private Boolean isActive;
}