package ra.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.exception.PromotionException;
import ra.model.dto.request.PromoApplyRequestDTO;
import ra.model.dto.request.PromotionRequestDTO;
import ra.model.entity.Order;
import ra.model.entity.Promotion;
import ra.repository.OrderRepository;
import ra.repository.PromotionRepository;
import ra.service.IPromotionService;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromotionServiceImpl implements IPromotionService {

    private final PromotionRepository promotionRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Promotion createPromotion(PromotionRequestDTO dto) {
        log.debug("[PROMO DEBUG] Đang tiến hành tạo mới chương trình khuyến mãi: {}", dto.getCode());

        Promotion promotion = new Promotion(null, dto.getCode(), dto.getDiscountPercent(), dto.getIsActive());
        Promotion saved = promotionRepository.save(promotion);

        log.info("[PROMO INFO] Khởi tạo mã giảm giá THÀNH CÔNG: ID={}, Code={}", saved.getId(), saved.getCode());
        return saved;
    }

    @Override
    @Transactional
    public Order applyPromotion(PromoApplyRequestDTO dto) {
        log.info("[PROMO INFO] Tiến hành áp dụng mã giảm giá '{}' cho Đơn hàng ID: {}", dto.getPromoCode(), dto.getOrderId());

        if ("EXPIRED".equalsIgnoreCase(dto.getPromoCode())) {
            log.warn("[PROMO WARN] Áp dụng thất bại. Mã khuyến mãi 'EXPIRED' đã hết hạn sử dụng.");
            throw new PromotionException("PROMO_INVALID", "Mã khuyến mãi này đã hết hạn sử dụng.");
        }

        if ("CRASH".equalsIgnoreCase(dto.getPromoCode())) {
            log.debug("[PROMO DEBUG] Kích hoạt bẫy dữ liệu CRASH hệ thống!");
            throw new NullPointerException("Lỗi giả lập con trỏ Null tại luồng tính toán tài chính!");
        }

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> {
                    log.warn("[PROMO WARN] Áp dụng thất bại. Đơn hàng ID {} không tồn tại.", dto.getOrderId());
                    return new PromotionException("ORDER_NOT_FOUND", "Đơn hàng yêu cầu không tồn tại.");
                });

        Promotion promotion = promotionRepository.findByCode(dto.getPromoCode())
                .orElseThrow(() -> {
                    log.warn("[PROMO WARN] Áp dụng thất bại. Mã khuyến mãi '{}' không tồn tại dưới DB.", dto.getPromoCode());
                    return new PromotionException("PROMO_INVALID", "Mã khuyến mãi không tồn tại trên hệ thống.");
                });

        if (!promotion.getIsActive()) {
            log.warn("[PROMO WARN] Áp dụng thất bại. Mã khuyến mãi '{}' đang bị vô hiệu hóa (isActive = false).", dto.getPromoCode());
            throw new PromotionException("PROMO_INVALID", "Mã khuyến mãi đã bị vô hiệu hóa.");
        }

        double discountAmount = order.getTotalAmount() * (promotion.getDiscountPercent() / 100.0);
        double finalAmount = order.getTotalAmount() - discountAmount;

        order.setDiscountAmount(discountAmount);
        order.setFinalAmount(finalAmount);
        Order updatedOrder = orderRepository.save(order);

        log.info("[PROMO SUCCESS] Áp dụng mã thành công! Đơn hàng ID: {} được giảm {}đ. Số tiền phải trả cuối cùng: {}đ",
                order.getId(), discountAmount, finalAmount);

        return updatedOrder;
    }
}