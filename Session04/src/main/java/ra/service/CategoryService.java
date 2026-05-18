package ra.service;

import org.springframework.data.domain.Page;
import ra.model.entity.Category;

import java.util.List;

public interface CategoryService {
    Page<Category> getCategories(Integer page, Integer size);
    Category getCategoryById(Long catId);
    Category insertCategory(Category cat);
    Category updateCategory(Long catId , Category cat);
    Boolean deleteCategory(Long catId); // xóa mềm
}
