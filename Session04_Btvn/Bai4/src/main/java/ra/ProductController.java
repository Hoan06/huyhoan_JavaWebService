package ra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private static final List<Product> mockProductDatabase = new ArrayList<>();

    static {
        mockProductDatabase.add(new Product("P001", "Laptop Dell XPS", 1500.0, 10));
        mockProductDatabase.add(new Product("P002", "iPhone 15 Pro", 1200.0, 25));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable("productId") String productId,
            @RequestBody Product productRequest) {

        Product existingProduct = null;
        for (Product p : mockProductDatabase) {
            if (p.getProductId().equalsIgnoreCase(productId)) {
                existingProduct = p;
                break;
            }
        }

        if (existingProduct == null) {
            return new ResponseEntity<>(
                    "Cập nhật thất bại: Không tìm thấy sản phẩm với ID: " + productId,
                    HttpStatus.NOT_FOUND
            );
        }

        existingProduct.setName(productRequest.getName());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setStock(productRequest.getStock());

        return new ResponseEntity<>(existingProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") String productId) {

        int targetIndex = -1;
        for (int i = 0; i < mockProductDatabase.size(); i++) {
            if (mockProductDatabase.get(i).getProductId().equalsIgnoreCase(productId)) {
                targetIndex = i;
                break;
            }
        }

        if (targetIndex == -1) {
            return new ResponseEntity<>(
                    "Xóa thất bại: Không tồn tại sản phẩm với ID: " + productId,
                    HttpStatus.NOT_FOUND
            );
        }

        mockProductDatabase.remove(targetIndex);

        return new ResponseEntity<>(
                "Xóa sản phẩm thành công! ID đã xóa: " + productId,
                HttpStatus.OK
        );
    }
}