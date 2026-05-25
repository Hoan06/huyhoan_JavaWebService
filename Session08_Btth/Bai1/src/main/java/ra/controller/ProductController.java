package ra.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.request.StockOutRequestDTO;
import ra.model.dto.request.StockRequestDTO;
import ra.service.ProductService;

@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/stock-in")
    public ResponseEntity<String> stockIn(
            @RequestHeader("X-User") String username,
            @RequestHeader("X-Role") String role,
            @Valid @RequestBody StockRequestDTO request) {

        productService.addProduct(request);
        return ResponseEntity.ok("Nhập kho thành công");
    }

    @PostMapping("/stock-out")
    public ResponseEntity<String> stockOut(
            @RequestHeader("X-User") String username,
            @RequestHeader("X-Role") String role,
            @Valid @RequestBody StockOutRequestDTO request) {

        productService.updateProduct(request);
        return ResponseEntity.ok("Xuất kho thành công");
    }

    @GetMapping("/inspect")
    public ResponseEntity<String> inspectInventory(@RequestHeader("X-Role") String role) {
        String report = productService.getInspect(role);
        return ResponseEntity.ok(report);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long id,
            @RequestHeader("X-User") String username,
            @RequestHeader("X-Role") String role) {

        productService.deleteProductById(id,role);
        return ResponseEntity.ok("Xóa sản phẩm thành công");
    }
}