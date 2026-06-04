package ra.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.demo.model.dto.response.ApiDataResponse;
import ra.demo.model.entity.Category;
import ra.demo.service.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<Category>>> getAllCategories(@RequestParam(name = "page", defaultValue = "1") Integer page) {
        Integer pageSize = 2;
        Page<Category> categories = categoryService.getCategories(page - 1, pageSize);
        log.info("{} - {}  - {}", "Nguyễn Văn Cường", "GET", "Lấy được " + categories.getTotalElements() + " danh mục sản phẩm!");
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách danh mục sản phẩm trang " + page + " thành công!",
                categories,
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @GetMapping("/{cateId}")
    public ResponseEntity<ApiDataResponse<Category>> getCategoryById(@PathVariable Long cateId) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh mục " + cateId + " thành công",
                categoryService.getCategoryById(cateId),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }
}
