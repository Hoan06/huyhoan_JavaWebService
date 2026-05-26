package ra;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class ShoppingCartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public ShoppingCartService(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    public void addProductToCart(String userId, String productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive integer");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setId(UUID.randomUUID().toString());
                    newCart.setUserId(userId);
                    newCart.setItems(new ArrayList<>());
                    return newCart;
                });

        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem item = existingItemOpt.get();
            int newQuantity = item.getQuantity() + quantity;
            if (product.getStockQuantity() < newQuantity) {
                throw new IllegalArgumentException("Not enough stock available");
            }
            item.setQuantity(newQuantity);
            item.setPrice(product.getPrice());
        } else {
            cart.getItems().add(new CartItem(productId, quantity, product.getPrice()));
        }

        cart.recalculateTotal();
        cartRepository.save(cart);
    }

    public void updateProductQuantity(String userId, String productId, int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be a positive integer");
        }

        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getStockQuantity() < newQuantity) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not in cart"));

        cartItem.setQuantity(newQuantity);
        cartItem.setPrice(product.getPrice());

        cart.recalculateTotal();
        cartRepository.save(cart);
    }


    public void removeProductFromCart(String userId, String productId) {
        ShoppingCart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));


        cart.getItems().removeIf(item -> item.getProductId().equals(productId));

        for (CartItem item : cart.getItems()) {
            productRepository.findById(item.getProductId())
                    .ifPresent(p -> item.setPrice(p.getPrice()));
        }

        cart.recalculateTotal();
        cartRepository.save(cart);
    }
}