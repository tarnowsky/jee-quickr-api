package pg.eti.kask.jee.quickr.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class VenueCapacityValidator implements ConstraintValidator<VenueCapacity, Integer> {

    @Override
    public void initialize(VenueCapacity constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle nulls
        }
        return value > 0 && value % 2 == 0;
    }
}
