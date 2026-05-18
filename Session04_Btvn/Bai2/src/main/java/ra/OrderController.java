package ra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order orderRequest) {

        String generatedId = UUID.randomUUID().toString();
        orderRequest.setOrderId(generatedId);

        Order savedOrder = orderRequest;

        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }
}