package ra.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.CartRequestDTO;
import ra.model.dto.response.CartResponseDTO;
import ra.service.ICartService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Slf4j
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addToCart(@Valid @RequestBody CartRequestDTO requestDTO) {
        log.info("API POST /api/cart/add được gọi bởi User: {}", requestDTO.getUserId());

        CartResponseDTO response = cartService.addToCart(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartResponseDTO>> getCart(@PathVariable String userId) {
        log.info("API GET /api/cart/{} được gọi.", userId);

        List<CartResponseDTO> response = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        log.info("API DELETE /api/cart/item/{} được gọi.", id);

        boolean deleted = cartService.deleteCartItem(id);
        if (deleted) {
            return ResponseEntity.ok("Xóa thành công sản phẩm khỏi giỏ hàng.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy mục giỏ hàng yêu cầu.");
    }
}