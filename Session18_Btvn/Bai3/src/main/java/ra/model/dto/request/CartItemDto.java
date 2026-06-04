package ra.model.dto.request;

import lombok.Data;

@Data
public class CartItemDto {
    private Long productId;
    private Integer quantity;
}