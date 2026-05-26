package ra.test_service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ra.demo.model.entity.Category;
import ra.demo.repository.CategoryRepository;
import ra.demo.service.CategoryService;
import ra.demo.service.impl.CategoryServiceImpl;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Test service - get all")
    void testGetAll(){
        List<Category> categories = List.of(
                new Category(null , "Điện tử 1" , true,null),
                new Category(null , "Điện tử 2" , true,null),
                new Category(null , "Điện tử 3" , true,null),
                new Category(null , "Điện tử 4" , true,null));

        Pageable pageable = PageRequest.of(1, 2);
        Page<Category> mockPageResult = new PageImpl<>(categories, pageable, categories.size());
        Mockito.when(categoryRepository.findAll(pageable)).thenReturn(mockPageResult);

        Page<Category> result = categoryService.getCategories(1,2);
        List<Category> categoryList = result.getContent();

        Assertions.assertNotNull(categoryList);
        Assertions.assertEquals(4, categoryList.size());
    }

    @Test
    @DisplayName("Test - test insert")
    void testInsert(){
        Category category = new Category(null,"Test thêm mới",true,null);

        Category category1 = new Category(6L , "Test thêm mới" , true,null);
        Mockito.when(categoryRepository.save(category)).thenReturn(category1);
        Category result = categoryService.insertCategory(category);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(6L, result.getCateId());
    }
}
