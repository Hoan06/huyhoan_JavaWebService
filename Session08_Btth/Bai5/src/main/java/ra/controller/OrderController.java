package ra.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.OrderRequest;
import ra.model.entity.StockOrder;
import ra.service.PlaceOrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final PlaceOrderService placeOrderService;

    public OrderController(PlaceOrderService placeOrderService) {
        this.placeOrderService = placeOrderService;
    }

    @PostMapping("/place")
    public ResponseEntity<StockOrder> placeOrder(
            @RequestHeader("X-User") String username,
            @Valid @RequestBody OrderRequest request) {

        StockOrder order = placeOrderService.placeOrder(request, username);
        return ResponseEntity.ok(order);
    }
}