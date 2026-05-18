package ra.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ra.model.dto.response.ApiDataResponse;
import ra.model.entity.Category;
import ra.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<Page<Category>>> getAllCategories(@RequestParam(name = "page" , defaultValue = "1") Integer page){
        Integer pageSize = 2;
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách danh mục sản phẩm thành công .",
                categoryService.getCategories(page-1,pageSize),
                HttpStatus.OK
        ) , HttpStatus.OK);
    }
}
