package ra.service;

import org.springframework.stereotype.Service;
import ra.model.entity.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final List<Order> orderList = new ArrayList<>();
    private long currentId = 1;

    public List<Order> getAllOrders() {
        return orderList;
    }

    public Optional<Order> getOrderById(Long id) {
        Optional<Order> order = orderList.stream().filter(o -> o.getId().equals(id)).findFirst();
        if (order.isEmpty()) {
            throw new RuntimeException("Order không tồn tại với ID: " + id);
        }
        return order;
    }

    public Order addOrder(Order order) {
        order.setId(currentId++);
        orderList.add(order);
        return order;
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order existingOrder = getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order không tồn tại với ID: " + id));

        existingOrder.setCustomerName(orderDetails.getCustomerName());
        existingOrder.setProduct(orderDetails.getProduct());
        existingOrder.setQuantity(orderDetails.getQuantity());
        existingOrder.setTotalAmount(orderDetails.getTotalAmount());
        return existingOrder;
    }

    public boolean deleteOrder(Long id) {
        Order order = getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order không tồn tại với ID: " + id));
        return orderList.remove(order);
    }
}