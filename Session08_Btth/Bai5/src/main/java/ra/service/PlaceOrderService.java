package ra.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.exception.MarginViolationException;
import ra.model.dto.request.OrderRequest;
import ra.model.entity.StockOrder;
import ra.repository.StockOrderRepository;

@Service
public class PlaceOrderService {

    private final StockOrderRepository stockOrderRepository;

    public PlaceOrderService(StockOrderRepository stockOrderRepository) {
        this.stockOrderRepository = stockOrderRepository;
    }

    @Transactional
    public StockOrder placeOrder(OrderRequest request, String username) {
        double referencePrice = 100.0;
        double priceDeviation = Math.abs(request.getPrice() - referencePrice) / referencePrice;

        if (priceDeviation > 0.07) {
            throw new MarginViolationException("Lệnh bị từ chối! Giá đặt mua vượt quá biên độ 7% so với giá tham chiếu.");
        }

        StockOrder order = new StockOrder();
        order.setStockCode(request.getStockCode());
        order.setQuantity(request.getQuantity());
        order.setPrice(request.getPrice());
        order.setOrderType(request.getOrderType());

        return stockOrderRepository.save(order);
    }
}