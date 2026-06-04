package ra.model.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private List<CartItemDto> items;
}