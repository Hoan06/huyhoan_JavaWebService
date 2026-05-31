package ra;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ra.model.entity.Product;
import ra.service.ProductService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductApplicationTests {

    @Autowired(required = false)
    private ProductService productService;

    @Autowired(required = false)
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testServiceGetAll_NotEmpty() {
        List<Product> list = productService.getAll();
        assertFalse(list.isEmpty());
    }

    @Test
    void testServiceGetById_Found() {
        Product product = productService.getById(1L);
        assertNotNull(product);
        assertEquals(1L, product.getId());
    }

    @Test
    void testServiceGetById_NotFound() {
        assertThrows(RuntimeException.class, () -> productService.getById(99L));
    }

    @Test
    void testServiceAddProduct_Success() {
        Product newProduct = new Product(null, "Keyboard Glow", 89.9, 10);
        Product saved = productService.addProduct(newProduct);
        assertNotNull(saved.getId());
    }

    @Test
    void testServiceDeleteProduct_Success() {
        int originalSize = productService.getAll().size();
        productService.deleteProduct(2L);
        int newSize = productService.getAll().size();
        assertEquals(originalSize - 1, newSize);
    }

    @Test
    void testControllerGetAll_Success_Http200() throws Exception {
        mockMvc.perform(get("/api/products").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testControllerGetById_NotFound_Http404() throws Exception {
        mockMvc.perform(get("/api/products/999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testControllerPost_Created_Http201() throws Exception {
        Product product = new Product(null, "Mouse Neon", 45.0, 5);
        String jsonContent = objectMapper.writeValueAsString(product);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void testControllerDelete_NoContent_Http204() throws Exception {
        Product temp = productService.addProduct(new Product(null, "Temp Product", 10.0, 1));

        mockMvc.perform(delete("/api/products/" + temp.getId()))
                .andExpect(status().isNoContent());
    }
}