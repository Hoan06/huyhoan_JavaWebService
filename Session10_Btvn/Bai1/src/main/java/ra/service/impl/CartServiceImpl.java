package ra.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ra.model.dto.request.CartRequestDTO;
import ra.model.dto.response.CartResponseDTO;
import ra.model.entity.CartItem;
import ra.repository.CartRepository;
import ra.service.ICartService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {

    private final CartRepository cartRepository;

    @Override
    public CartResponseDTO addToCart(CartRequestDTO dto) {
        log.info("Bắt đầu xử lý nghiệp vụ thêm giỏ hàng. User: {}, Product: {}, Quantity: {}",
                dto.getUserId(), dto.getProductId(), dto.getQuantity());

        Optional<CartItem> existingItemOpt = cartRepository.findByUserIdAndProductId(dto.getUserId(), dto.getProductId());
        CartItem savedItem;

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            int newQuantity = existingItem.getQuantity() + dto.getQuantity();

            log.info("Sản phẩm đã tồn tại trong giỏ hàng. Tiến hành cộng dồn số lượng: {} + {} = {}",
                    existingItem.getQuantity(), dto.getQuantity(), newQuantity);

            existingItem.setQuantity(newQuantity);
            savedItem = cartRepository.save(existingItem);
        } else {
            log.info("Sản phẩm chưa tồn tại trong giỏ hàng. Tạo mới bản ghi giỏ hàng.");
            CartItem newItem = new CartItem(null, dto.getUserId(), dto.getProductId(), dto.getQuantity());
            savedItem = cartRepository.save(newItem);
        }

        log.info("Xử lý thêm giỏ hàng THÀNH CÔNG. CartItemID: {}", savedItem.getId());
        return new CartResponseDTO(savedItem.getId(), savedItem.getUserId(), savedItem.getProductId(), savedItem.getQuantity());
    }

    @Override
    public List<CartResponseDTO> getCartByUserId(String userId) {
        log.info("Truy vấn danh sách giỏ hàng cho người dùng: {}", userId);
        List<CartItem> items = cartRepository.findByUserId(userId);
        log.info("Tìm thấy {} sản phẩm trong giỏ hàng của user {}", items.size(), userId);

        return items.stream()
                .map(item -> new CartResponseDTO(item.getId(), item.getUserId(), item.getProductId(), item.getQuantity()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteCartItem(Long id) {
        log.info("Yêu cầu xóa sản phẩm khỏi giỏ hàng. CartItemID: {}", id);
        boolean isDeleted = cartRepository.deleteById(id);
        if (isDeleted) {
            log.info("Xóa CartItemID: {} THÀNH CÔNG", id);
        } else {
            log.warn("Không thể xóa. Không tìm thấy CartItemID: {}", id);
        }
        return isDeleted;
    }
}