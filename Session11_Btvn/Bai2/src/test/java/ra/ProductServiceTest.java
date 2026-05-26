package ra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductService.ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private ProductService.Product mockProduct;

    @BeforeEach
    void setUp() {
        mockProduct = new ProductService.Product("PROD-01", 50);
    }

    @Test
    void shouldIncreaseStock_whenAddingValidQuantity() {
        when(productRepository.findById("PROD-01")).thenReturn(Optional.of(mockProduct));

        int newStock = productService.updateStock("PROD-01", 20);

        assertThat(newStock).isEqualTo(70);
        assertThat(mockProduct.getStockQuantity()).isEqualTo(70);
        verify(productRepository, times(1)).save(mockProduct);
    }

    @Test
    void shouldDecreaseStock_whenSubtractingValidQuantity() {
        when(productRepository.findById("PROD-01")).thenReturn(Optional.of(mockProduct));

        int newStock = productService.updateStock("PROD-01", -30);

        assertThat(newStock).isEqualTo(20);
        assertThat(mockProduct.getStockQuantity()).isEqualTo(20);
        verify(productRepository, times(1)).save(mockProduct);
    }

    @Test
    void shouldThrowException_whenSubtractingMoreThanCurrentStock() {
        when(productRepository.findById("PROD-01")).thenReturn(Optional.of(mockProduct));

        assertThatThrownBy(() -> productService.updateStock("PROD-01", -60))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Resulting stock would be negative");

        verify(productRepository, never()).save(any(ProductService.Product.class));
    }

    @Test
    void shouldThrowException_whenProductDoesNotExist() {
        when(productRepository.findById("INVALID-ID")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.updateStock("INVALID-ID", 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product not found with ID: INVALID-ID");

        verify(productRepository, never()).save(any());
    }

    @Test
    void shouldCallSaveWithCorrectProductState() {
        when(productRepository.findById("PROD-01")).thenReturn(Optional.of(mockProduct));

        productService.updateStock("PROD-01", 15);

        ArgumentCaptor<ProductService.Product> productCaptor = ArgumentCaptor.forClass(ProductService.Product.class);
        verify(productRepository).save(productCaptor.capture());

        ProductService.Product savedProduct = productCaptor.getValue();
        assertThat(savedProduct.getId()).isEqualTo("PROD-01");
        assertThat(savedProduct.getStockQuantity()).isEqualTo(65); // 50 + 15
    }
}