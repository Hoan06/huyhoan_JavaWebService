package ra.controller;

import ra.model.dto.request.OrderSummaryDTO;
import ra.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/my-summary")
    public ResponseEntity<OrderSummaryDTO> getMySummary() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(orderService.getMySummary(username));
    }
}