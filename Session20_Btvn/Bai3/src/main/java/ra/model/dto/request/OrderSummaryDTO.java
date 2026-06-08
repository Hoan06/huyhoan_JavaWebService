package ra.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderSummaryDTO {
    private Double totalSpent;
    private List<String> purchasedItems;
}
