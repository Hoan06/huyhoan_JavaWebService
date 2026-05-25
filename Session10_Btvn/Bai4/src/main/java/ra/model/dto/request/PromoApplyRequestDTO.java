package ra.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PromoApplyRequestDTO {
    @NotNull(message = "ID đơn hàng không được để trống")
    private Long orderId;

    @NotBlank(message = "Mã giảm giá không được để trống")
    private String promoCode;
}