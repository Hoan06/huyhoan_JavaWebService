package ra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.model.dto.response.ApiDataResponse;
import ra.model.entity.Product;
import ra.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v36/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<Product>>> getAlls(@RequestParam(value = "page" , defaultValue = "1") Integer page ,
                                                                  @RequestParam(value = "sort" , defaultValue = "asc") String sort) {
        int size = 2;
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách thành công.",
                productService.getAlls(page,size,sort),
                HttpStatus.OK
        ) , HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<Product>> save(@RequestBody Product product) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Thêm mới sản phẩm thành công.",
                productService.insert(product),
                HttpStatus.CREATED
        ) ,  HttpStatus.CREATED);
    }

    @PutMapping("/{proId}")
    public ResponseEntity<ApiDataResponse<Product>> update(@PathVariable Long proId , @RequestBody Product product) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật sản phẩm thành công.",
                productService.update(proId,product),
                HttpStatus.OK
        ) , HttpStatus.OK);
    }

    @DeleteMapping("/{proId}")
    public ResponseEntity<ApiDataResponse<Boolean>> delete(@PathVariable Long proId) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Xóa sản phẩm thành công.",
                productService.delete(proId),
                HttpStatus.NO_CONTENT
        ) , HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{proId}")
    public ResponseEntity<ApiDataResponse<Product>> get(@PathVariable Long proId) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy sản phẩm thành công.",
                productService.findById(proId),
                HttpStatus.OK
        ) , HttpStatus.OK);
    }
}
