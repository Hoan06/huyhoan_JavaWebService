package ra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.response.ApiDataResponse;
import ra.model.entity.Product;
import ra.service.ProductService;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<Product>>> getAllProducts(@RequestParam(name = "page" , defaultValue = "1") Integer page, @RequestParam(name = "size" , defaultValue = "10") Integer size) {
        Integer pageSize = 2;
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách sản phẩm thành công .",
                productService.getAllProducts(page-1,pageSize),
                HttpStatus.OK
        ) , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<Product>> insertProduct(@RequestBody Product product) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Thêm mới sản phẩm thành công",
                productService.insertProdcut(product),
                HttpStatus.CREATED
        ) , HttpStatus.CREATED);
    }

    @PatchMapping("/{proId}")
    public ResponseEntity<ApiDataResponse<Product>> updateProduct(@PathVariable Long proId, @RequestBody Product product) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật sản phẩm thành công",
                productService.updateProdcut(proId,product),
                HttpStatus.OK
        ) , HttpStatus.OK);
    }
}