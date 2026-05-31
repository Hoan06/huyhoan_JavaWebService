package ra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ra.model.entity.Order;
import ra.service.OrderService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getAllOrders_ReturnNonEmptyList() {
        orderService.addOrder(new Order(null, "Hoan Nguyen", "iPhone 15", 1, 1000.0));

        List<Order> result = orderService.getAllOrders();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void getOrderById_Found() {
        Order saved = orderService.addOrder(new Order(null, "Hoan Nguyen", "iPhone 15", 1, 1000.0));

        Optional<Order> result = orderService.getOrderById(saved.getId());

        assertTrue(result.isPresent());
        assertEquals("Hoan Nguyen", result.get().getCustomerName());
    }

    @Test
    void getOrderById_NotFound_ThrowException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(99L);
        });

        assertTrue(exception.getMessage().contains("Order không tồn tại với ID"));
    }

    @Test
    void addOrder_Success() {
        Order newOrder = new Order(null, "Alice", "Macbook", 2, 4000.0);

        Order saved = orderService.addOrder(newOrder);

        assertNotNull(saved.getId());
        assertEquals("Alice", saved.getCustomerName());
    }

    @Test
    void updateOrder_Success() {
        Order saved = orderService.addOrder(new Order(null, "Hoan Nguyen", "iPhone 15", 1, 1000.0));
        Order updateDetails = new Order(null, "Hoan Nguyen Updated", "iPhone 15 Pro", 2, 2400.0);

        Order updated = orderService.updateOrder(saved.getId(), updateDetails);

        assertEquals("Hoan Nguyen Updated", updated.getCustomerName());
        assertEquals("iPhone 15 Pro", updated.getProduct());
        assertEquals(2, updated.getQuantity());
    }

    @Test
    void deleteOrder_RemovesElement() {
        Order saved = orderService.addOrder(new Order(null, "Hoan Nguyen", "iPhone 15", 1, 1000.0));
        int sizeBefore = orderService.getAllOrders().size();

        boolean isDeleted = orderService.deleteOrder(saved.getId());

        assertTrue(isDeleted);
        assertEquals(sizeBefore - 1, orderService.getAllOrders().size());
    }
}