package ra;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ra.controller.OrderController;
import ra.model.entity.Order;
import ra.service.OrderService;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGet_AllOrders_Return200AndJsonArray() throws Exception {
        Order o1 = new Order(1L, "Hoan Nguyen", "Java Book", 1, 50.0);
        Order o2 = new Order(2L, "Alice", "React Book", 2, 80.0);
        Mockito.when(orderService.getAllOrders()).thenReturn(Arrays.asList(o1, o2));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].customerName").value("Hoan Nguyen"))
                .andExpect(jsonPath("$[1].customerName").value("Alice"));
    }

    @Test
    void testGet_OrderById_Found_Return200() throws Exception {
        Order order = new Order(1L, "Hoan Nguyen", "Java Book", 1, 50.0);
        Mockito.when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerName").value("Hoan Nguyen"));
    }

    @Test
    void testGet_OrderById_NotFound_Return404() throws Exception {
        Mockito.when(orderService.getOrderById(99L)).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(get("/api/orders/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPost_CreateOrder_Return201AndBodyWithId() throws Exception {
        Order inputOrder = new Order(null, "Bob", "Spring Boot Course", 1, 150.0);
        Order savedOrder = new Order(1L, "Bob", "Spring Boot Course", 1, 150.0);

        Mockito.when(Mockito.any(Order.class)).thenReturn(savedOrder);
        Mockito.when(orderService.addOrder(Mockito.any(Order.class))).thenReturn(savedOrder);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputOrder)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerName").value("Bob"));
    }
}