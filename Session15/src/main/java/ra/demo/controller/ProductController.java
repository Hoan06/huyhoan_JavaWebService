package ra.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.demo.model.dto.response.ApiDataResponse;
import ra.demo.model.entity.Product;
import ra.demo.service.ProductService;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<Product>>> getProducts(@RequestParam(name = "page", defaultValue = "1") Integer page) {
        Integer pageSize = 2;
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách sản phẩm trang " + page + " thành công!",
                productService.getAllProducts(page - 1, pageSize),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<Product>> insertProduct(@RequestBody Product product) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Thêm mới sản phẩm " + product.getProName() + " thành công!",
                productService.insertProduct(product),
                 null,
                HttpStatus.CREATED
        ), HttpStatus.CREATED);
    }

    @PatchMapping("/{proId}")
    public ResponseEntity<ApiDataResponse<Product>> updatePartialProduct(@RequestBody Product product, @PathVariable("proId") Long proId) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật sản phẩm " + proId + " thành công",
                productService.updatePartialProduct(proId, product),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }
}
