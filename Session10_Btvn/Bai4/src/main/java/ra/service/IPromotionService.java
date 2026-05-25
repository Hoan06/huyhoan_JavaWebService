package ra.service;


import ra.model.dto.request.PromoApplyRequestDTO;
import ra.model.dto.request.PromotionRequestDTO;
import ra.model.entity.Order;
import ra.model.entity.Promotion;

public interface IPromotionService {
    Promotion createPromotion(PromotionRequestDTO dto);
    Order applyPromotion(PromoApplyRequestDTO dto);
}
