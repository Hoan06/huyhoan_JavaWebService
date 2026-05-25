package ra.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartResponseDTO {
    private Long id;
    private String userId;
    private String productId;
    private Integer quantity;
}