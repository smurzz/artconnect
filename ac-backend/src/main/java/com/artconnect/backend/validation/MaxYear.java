package com.artconnect.backend.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Year;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxYearValidator.class)
@Documented
public @interface MaxYear {

    String message() default "Year of creation must be less than or equal to the current year";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

class MaxYearValidator implements ConstraintValidator<MaxYear, Integer> {

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        int currentYear = Year.now().getValue();
        return year == null || year <= currentYear;
    }
}
