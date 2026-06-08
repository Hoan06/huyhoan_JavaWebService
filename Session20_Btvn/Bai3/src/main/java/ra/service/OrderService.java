package ra.service;

import ra.model.dto.request.OrderSummaryDTO;
import ra.model.entity.AppUser;
import ra.model.entity.PurchaseOrder;
import ra.model.entity.UserToken;
import ra.repository.AppUserRepository;
import ra.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ra.repository.PurchaseOrderRepository;
import ra.repository.UserTokenRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final AppUserRepository appUserRepository;
    private final ProductRepository productRepository;
    private final UserTokenRepository userTokenRepository;

    public OrderSummaryDTO getMySummary(String username) {
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        UserToken token = (UserToken) userTokenRepository.findByUserIdAndIsRevokedFalse(user.getId());

        if (token.isRevoked() || token.isExpired()) {
            throw new RuntimeException("Refresh Token đã bị hủy hoặc hết hạn");
        }

        List<PurchaseOrder> orders = purchaseOrderRepository.findByUserId(user.getId());

        Double totalSpent = orders.stream()
                .filter(order -> "COMPLETED".equals(order.getStatus()))
                .flatMap(order -> order.getOrderItems().stream())
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum();

        List<String> purchasedItems = orders.stream()
                .filter(order -> "COMPLETED".equals(order.getStatus()))
                .flatMap(order -> order.getOrderItems().stream())
                .map(item -> productRepository.findById(item.getProductId()).get().getName())
                .distinct()
                .collect(Collectors.toList());

        return new OrderSummaryDTO(totalSpent, purchasedItems);
    }
}