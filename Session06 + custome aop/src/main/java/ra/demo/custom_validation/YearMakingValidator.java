package ra.demo.custom_validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class YearMakingValidator implements ConstraintValidator<YearMakingValidation, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if(value==null){
            return true;
        }
        if(value>=1990){
            return true;
        }
        return false;
    }
}
