package ra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    private Product mockProduct;
    private ShoppingCart mockCart;

    @BeforeEach
    void setUp() {
        mockProduct = new Product("PROD-X", "Sản phẩm X", 100000.0, 10);

        mockCart = new ShoppingCart();
        mockCart.setId("CART-01");
        mockCart.setUserId("USER-A");
        mockCart.setItems(new ArrayList<>());
    }


    @Test
    @DisplayName("Thêm sản phẩm thành công vào giỏ hàng ĐÃ TỒN TẠI (Happy Path)")
    void addProductToCart_Success_ExistingCart() {
        when(cartRepository.findByUserId("USER-A")).thenReturn(Optional.of(mockCart));
        when(productRepository.findById("PROD-X")).thenReturn(Optional.of(mockProduct));

        shoppingCartService.addProductToCart("USER-A", "PROD-X", 3);

        ArgumentCaptor<ShoppingCart> cartCaptor = ArgumentCaptor.forClass(ShoppingCart.class);
        verify(cartRepository, times(1)).save(cartCaptor.capture());

        ShoppingCart savedCart = cartCaptor.getValue();
        assertThat(savedCart.getItems()).hasSize(1);
        assertThat(savedCart.getItems().get(0).getQuantity()).isEqualTo(3);
        assertThat(savedCart.getItems().get(0).getPrice()).isEqualTo(100000.0); // Quy tắc giá hiện hành
    }

    @Test
    @DisplayName("Tự động tạo giỏ hàng MỚI khi người dùng chưa có giỏ hàng (Yêu cầu đặc biệt)")
    void addProductToCart_Success_CreateNewCart() {
        when(cartRepository.findByUserId("USER-A")).thenReturn(Optional.empty());
        when(productRepository.findById("PROD-X")).thenReturn(Optional.of(mockProduct));

        shoppingCartService.addProductToCart("USER-A", "PROD-X", 2);

        ArgumentCaptor<ShoppingCart> cartCaptor = ArgumentCaptor.forClass(ShoppingCart.class);
        verify(cartRepository, times(1)).save(cartCaptor.capture());

        ShoppingCart savedCart = cartCaptor.getValue();
        assertThat(savedCart.getUserId()).isEqualTo("USER-A");
        assertThat(savedCart.getItems()).hasSize(1);
    }

    @Test
    @DisplayName("Thêm sản phẩm thất bại do vượt quá số lượng tồn kho (Unhappy Path)")
    void addProductToCart_Fail_InsufficientStock() {
        when(cartRepository.findByUserId("USER-A")).thenReturn(Optional.of(mockCart));
        when(productRepository.findById("PROD-X")).thenReturn(Optional.of(mockProduct)); // Stock = 10

        assertThatThrownBy(() -> shoppingCartService.addProductToCart("USER-A", "PROD-X", 11))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not enough stock available");

        verify(cartRepository, never()).save(any());
    }

    @Test
    @DisplayName("Thêm sản phẩm thất bại do sản phẩm không tồn tại trong hệ thống (Unhappy Path)")
    void addProductToCart_Fail_ProductNotFound() {
        when(cartRepository.findByUserId("USER-A")).thenReturn(Optional.of(mockCart));
        when(productRepository.findById("INVALID-ID")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> shoppingCartService.addProductToCart("USER-A", "INVALID-ID", 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product not found");

        verify(cartRepository, never()).save(any());
    }


    @Test
    @DisplayName("Cập nhật số lượng thành công khi kho đáp ứng đủ (Happy Path)")
    void updateProductQuantity_Success() {
        CartItem existingItem = new CartItem("PROD-X", 2, 100000.0);
        mockCart.getItems().add(existingItem);

        when(cartRepository.findByUserId("USER-A")).thenReturn(Optional.of(mockCart));
        when(productRepository.findById("PROD-X")).thenReturn(Optional.of(mockProduct)); // Stock = 10

        shoppingCartService.updateProductQuantity("USER-A", "PROD-X", 5);

        ArgumentCaptor<ShoppingCart> cartCaptor = ArgumentCaptor.forClass(ShoppingCart.class);
        verify(cartRepository).save(cartCaptor.capture());

        assertThat(cartCaptor.getValue().getItems().get(0).getQuantity()).isEqualTo(5);
    }

    @Test
    @DisplayName("Cập nhật thất bại khi số lượng truyền vào không dương (Unhappy Path)")
    void updateProductQuantity_Fail_InvalidQuantity() {
        CartItem existingItem = new CartItem("PROD-X", 2, 100000.0);
        mockCart.getItems().add(existingItem);

        when(cartRepository.findByUserId("USER-A")).thenReturn(Optional.of(mockCart));

        assertThatThrownBy(() -> shoppingCartService.updateProductQuantity("USER-A", "PROD-X", 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Quantity must be a positive integer");

        verify(cartRepository, never()).save(any());
    }

    @Test
    @DisplayName("Phân tích Phần 1: Cập nhật thất bại vì tồn kho đã bị người khác mua hết trước đó")
    void updateProductQuantity_Fail_StockReducedByAnotherUser() {
        CartItem existingItem = new CartItem("PROD-X", 5, 100000.0);
        mockCart.getItems().add(existingItem);

        mockProduct.setStockQuantity(7);

        when(cartRepository.findByUserId("USER-A")).thenReturn(Optional.of(mockCart));
        when(productRepository.findById("PROD-X")).thenReturn(Optional.of(mockProduct));

        assertThatThrownBy(() -> shoppingCartService.updateProductQuantity("USER-A", "PROD-X", 8))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Not enough stock available");

        verify(cartRepository, never()).save(any());
    }


    @Test
    @DisplayName("Xóa sản phẩm khỏi giỏ hàng thành công (Happy Path)")
    void removeProductFromCart_Success() {
        CartItem item = new CartItem("PROD-X", 2, 100000.0);
        mockCart.getItems().add(item);

        when(cartRepository.findByUserId("USER-A")).thenReturn(Optional.of(mockCart));

        shoppingCartService.removeProductFromCart("USER-A", "PROD-X");

        ArgumentCaptor<ShoppingCart> cartCaptor = ArgumentCaptor.forClass(ShoppingCart.class);
        verify(cartRepository).save(cartCaptor.capture());

        assertThat(cartCaptor.getValue().getItems()).isEmpty();
    }

    @Test
    @DisplayName("Xử lý sạch sẽ khi xóa sản phẩm đã bị xóa hoàn toàn khỏi hệ thống DB (Yêu cầu đặc biệt)")
    void removeProductFromCart_Success_EvenWhenProductDeletedFromSystem() {
        CartItem item = new CartItem("DELETED-PROD", 1, 50000.0);
        mockCart.getItems().add(item);

        when(cartRepository.findByUserId("USER-A")).thenReturn(Optional.of(mockCart));
        when(productRepository.findById("DELETED-PROD")).thenReturn(Optional.empty());

        shoppingCartService.removeProductFromCart("USER-A", "DELETED-PROD");

        ArgumentCaptor<ShoppingCart> cartCaptor = ArgumentCaptor.forClass(ShoppingCart.class);
        verify(cartRepository).save(cartCaptor.capture());

        assertThat(cartCaptor.getValue().getItems()).isEmpty();
    }
}