package ra.test_repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import ra.demo.model.entity.Category;
import ra.demo.repository.CategoryRepository;

import java.util.List;

@DataJpaTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Category - Repository")
    void testFindAll() {
        List<Category> categories = List.of(
        new Category(null , "Điện tử 1" , true,null),
        new Category(null , "Điện tử 2" , true,null),
        new Category(null , "Điện tử 3" , true,null),
        new Category(null , "Điện tử 4" , true,null));

        categoryRepository.saveAllAndFlush(categories);

        List<Category> categories2 = categoryRepository.findAll();
        Assertions.assertEquals(4,categories2.size());

    }

    @Test
    @DisplayName("Category - test save")
    void testSave() {
        Category category = new Category(null,"Test 1",true,null);
        Category result = categoryRepository.save(category);
        Assertions.assertNotNull(result);
    }

}
