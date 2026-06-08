package ra.model.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class CheckoutRequest {
    private List<CheckoutItemRequest> items;
}