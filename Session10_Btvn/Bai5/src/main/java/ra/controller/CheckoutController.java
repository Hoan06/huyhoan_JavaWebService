package ra.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.CheckoutRequestDTO;
import ra.service.IInventoryService;
import ra.service.IOrderService;
import ra.service.IPaymentService;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class CheckoutController {

    private final IOrderService orderService;
    private final IPaymentService paymentService;
    private final IInventoryService inventoryService;

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@Valid @RequestBody CheckoutRequestDTO requestDTO) {
        log.info("[CheckoutController] Nhận yêu cầu kích hoạt Chuỗi Quy Trình Thanh Toán Đơn Hàng.");

        orderService.createOrder(requestDTO);
        paymentService.processPayment(requestDTO);
        inventoryService.updateInventory(requestDTO.getOrderCode());

        return ResponseEntity.ok("Xử lý chuỗi đơn hàng thành công.");
    }
}