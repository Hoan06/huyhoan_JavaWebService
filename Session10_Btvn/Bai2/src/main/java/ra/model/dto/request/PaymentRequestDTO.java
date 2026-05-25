package ra.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentRequestDTO {
    @NotNull(message = "OrderId không được để trống")
    private Long orderId;

    @NotNull(message = "UserId không được để trống")
    private Long userId;

    @NotNull(message = "Số tiền thanh toán không được để trống")
    @Positive(message = "Số tiền thanh toán phải lớn hơn 0")
    private Double amount;
}