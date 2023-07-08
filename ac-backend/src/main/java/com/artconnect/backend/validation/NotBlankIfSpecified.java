package com.artconnect.backend.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NotBlankIfSpecifiedValidator.class})
public @interface NotBlankIfSpecified {
    String message() default "Field must not be blank";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class NotBlankIfSpecifiedValidator implements ConstraintValidator<NotBlankIfSpecified, String> {

    @Override
    public void initialize(NotBlankIfSpecified constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Not validating if the value is not specified
        }
        return !value.trim().isEmpty(); // Validate as @NotBlank if the value is specified
    }
}

