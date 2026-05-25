package ra.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CheckoutRequestDTO {
    @NotBlank(message = "Mã đơn hàng không được để trống")
    private String orderCode;

    @NotNull(message = "Số tiền không được để trống")
    @Positive(message = "Số tiền thanh toán phải lớn hơn 0")
    private Double amount;
}