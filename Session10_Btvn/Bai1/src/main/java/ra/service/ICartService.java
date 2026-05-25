package ra.service;

import ra.model.dto.request.CartRequestDTO;
import ra.model.dto.response.CartResponseDTO;

import java.util.List;

public interface ICartService {
    CartResponseDTO addToCart(CartRequestDTO requestDTO);
    List<CartResponseDTO> getCartByUserId(String userId);
    boolean deleteCartItem(Long id);
}