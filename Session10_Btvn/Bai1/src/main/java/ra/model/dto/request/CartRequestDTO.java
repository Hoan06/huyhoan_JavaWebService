package ra.model.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartRequestDTO {

    @NotBlank(message = "ID người dùng không được để trống")
    private String userId;

    @NotBlank(message = "ID sản phẩm không được để trống")
    private String productId;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng sản phẩm thêm vào giỏ hàng phải lớn hơn hoặc bằng 1")
    private Integer quantity;
}