package ra.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.entity.Category;
import ra.repository.CategoryRepository;
import ra.service.CategoryService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Page<Category> getCategories(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category getCategoryById(Long catId) {
        return categoryRepository.findById(catId).orElseThrow(() -> new NoSuchElementException("Không tồn tại id : " + catId));
    }

    @Override
    public Category insertCategory(Category cat) {
        return categoryRepository.save(cat);
    }

    @Override
    public Category updateCategory(Long catId, Category cat) {
        categoryRepository.findById(catId).orElseThrow(() -> new NoSuchElementException("Không tồn tại id : " + catId));
        cat.setCateId(catId);
        return categoryRepository.save(cat);
    }

    @Override
    public Boolean deleteCategory(Long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() -> new NoSuchElementException("Không tồn tại id : " + catId));
        category.setStatus(false);
        categoryRepository.save(category);
        return true;
    }
}
