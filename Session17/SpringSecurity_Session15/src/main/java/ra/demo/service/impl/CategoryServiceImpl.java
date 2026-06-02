package ra.demo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.demo.model.entity.Category;
import ra.demo.repository.CategoryRepository;
import ra.demo.service.CategoryService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Page<Category> getCategories(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category getCategoryById(Long cateId) {
        return categoryRepository.findById(cateId).orElseThrow(()->
        {
            log.error("{} - {} - {}","Nguyễn Mạnh Cường","FIND","Danh mục "+cateId+" không tồn tại!");
            return new NoSuchElementException("Không tồn tại danh mục: "+cateId);
        });
    }

    @Override
    public Category insertCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long cateId, Category category) {
        categoryRepository.findById(cateId).orElseThrow(()-> new NoSuchElementException("Không tồn tại danh mục: "+cateId));
        category.setCateId(cateId);
        return categoryRepository.save(category);
    }

    @Override
    public Category deleteCategory(Long cateId) {
        Category category = categoryRepository.findById(cateId).orElseThrow(() -> new NoSuchElementException("Không tồn tại danh mục: " + cateId));
        category.setStatus(false);
        return categoryRepository.save(category);
    }
}
