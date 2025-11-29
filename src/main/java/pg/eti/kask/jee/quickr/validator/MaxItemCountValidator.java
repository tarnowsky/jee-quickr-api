package pg.eti.kask.jee.quickr.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MaxItemCountValidator implements ConstraintValidator<MaxItemCount, Integer> {

    private int max;

    @Override
    public void initialize(MaxItemCount constraintAnnotation) {
        this.max = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle nulls
        }
        return value <= max;
    }
}
