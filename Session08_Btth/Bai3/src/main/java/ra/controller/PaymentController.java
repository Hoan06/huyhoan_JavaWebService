package ra.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ra.model.dto.request.PaymentRequest;
import ra.model.dto.request.RefundRequest;
import ra.model.entity.Transaction;
import ra.service.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/domestic")
    public ResponseEntity<Transaction> domesticPayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.processDomesticPayment(request));
    }

    @PostMapping("/international")
    public ResponseEntity<Transaction> internationalPayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.processInternationalPayment(request));
    }

    @PostMapping("/refund")
    public ResponseEntity<Transaction> refundPayment(@Valid @RequestBody RefundRequest request) {
        return ResponseEntity.ok(paymentService.processRefund(request));
    }
}