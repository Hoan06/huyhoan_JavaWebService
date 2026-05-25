package ra.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.PromoApplyRequestDTO;
import ra.model.dto.request.PromotionRequestDTO;
import ra.model.entity.Order;
import ra.model.entity.Promotion;
import ra.service.IPromotionService;

@RestController
@RequestMapping("/api/promo")
@RequiredArgsConstructor
public class PromotionController {

    private final IPromotionService promotionService;

    @PostMapping
    public ResponseEntity<Promotion> createPromo(@Valid @RequestBody PromotionRequestDTO requestDTO) {
        Promotion created = promotionService.createPromotion(requestDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/apply")
    public ResponseEntity<Order> applyPromo(@Valid @RequestBody PromoApplyRequestDTO requestDTO) {
        Order updatedOrder = promotionService.applyPromotion(requestDTO);
        return ResponseEntity.ok(updatedOrder);
    }
}