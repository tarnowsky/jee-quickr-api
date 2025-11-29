package pg.eti.kask.jee.quickr.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = VenueCapacityValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface VenueCapacity {
    String message() default "Capacity must be a positive even number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
