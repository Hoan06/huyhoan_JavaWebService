package ra.custom_valid.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ra.custom_valid.MedicationStatusValid;
import ra.model.entity.MedicationStatus; // Import enum của bạn vào đây

public class MedicationStatusValidImpl implements ConstraintValidator<MedicationStatusValid, MedicationStatus> {

    @Override
    public boolean isValid(MedicationStatus value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String statusName = value.name();
        return statusName.equalsIgnoreCase("AVAILABLE") || statusName.equalsIgnoreCase("OUT_OF_STOCK");
    }
}