package ra.model.dto.request;

import lombok.Data;

@Data
public class CheckoutItemRequest {
    private Long ticketCategoryId;
    private Integer quantity;
}