package ra.demo.service;

import org.springframework.data.domain.Page;
import ra.demo.model.entity.Category;

public interface CategoryService {
    Page<Category> getCategories(Integer page, Integer pageSize); //có xử lý phân trang
    Category getCategoryById(Long cateId);
    Category insertCategory(Category category);
    Category updateCategory(Long cateId, Category category);
    Category deleteCategory(Long cateId); // xóa mềm
}
