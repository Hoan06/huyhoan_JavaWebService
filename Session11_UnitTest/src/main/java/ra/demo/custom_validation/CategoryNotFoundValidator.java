package ra.demo.custom_validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import ra.demo.model.entity.Category;
import ra.demo.repository.CategoryRepository;

@RequiredArgsConstructor
public class CategoryNotFoundValidator implements ConstraintValidator<CategoryNotFound, Category> {
    private final CategoryRepository categoryRepository;

    @Override
    public boolean isValid(Category value, ConstraintValidatorContext context) {
        if(value==null){
            return true;
        }
        if(categoryRepository.findById(value.getCateId()).orElse(null)!=null){
            return true;
        }
        return false;
    }
}
