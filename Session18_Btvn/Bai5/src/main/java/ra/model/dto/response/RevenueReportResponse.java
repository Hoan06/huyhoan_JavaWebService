package ra.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueReportResponse {
    private String period;
    private BigDecimal totalRevenue;
    private Long totalOrders;
}