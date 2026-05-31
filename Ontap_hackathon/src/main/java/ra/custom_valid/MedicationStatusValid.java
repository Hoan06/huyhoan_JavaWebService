package ra.custom_valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ra.custom_valid.impl.MedicationStatusValidImpl;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {MedicationStatusValidImpl.class})
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface MedicationStatusValid {
    String message() default "Chỉ nhập 1 trong 2 trạng thái 'AVAILABLE' hoặc 'OUT_OF_STOCK' !";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
