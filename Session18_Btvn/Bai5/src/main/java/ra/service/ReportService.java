package ra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.dto.response.RevenueReportResponse;
import ra.model.entity.Order;
import ra.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private OrderRepository orderRepository;

    public List<RevenueReportResponse> getRevenueReport(String type) {
        List<Order> completedOrders = orderRepository.findAll().stream()
                .filter(order -> "COMPLETED".equalsIgnoreCase(order.getStatus()))
                .collect(Collectors.toList());

        final String pattern = "day".equalsIgnoreCase(type) ? "yyyy-MM-dd"
                : "year".equalsIgnoreCase(type) ? "yyyy"
                : "yyyy-MM";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        Map<String, List<Order>> groupedOrders = completedOrders.stream()
                .collect(Collectors.groupingBy(order -> order.getCreatedDate().format(formatter)));

        return groupedOrders.entrySet().stream()
                .map(entry -> {
                    String period = entry.getKey();
                    List<Order> orders = entry.getValue();

                    BigDecimal totalRevenue = orders.stream()
                            .map(Order::getTotalMoney)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    Long totalOrders = (long) orders.size();

                    return new RevenueReportResponse(period, totalRevenue, totalOrders);
                })
                .collect(Collectors.toList());
    }
}