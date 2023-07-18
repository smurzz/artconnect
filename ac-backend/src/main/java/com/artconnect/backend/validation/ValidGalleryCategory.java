package com.artconnect.backend.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import com.artconnect.backend.model.gallery.GalleryCategory;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumStringValidator.class)
public @interface ValidGalleryCategory {

    String message() default "Invalid gallery category";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

class EnumStringValidator implements ConstraintValidator<ValidGalleryCategory, Set<String>> {

    private Set<String> enumValues;

    @Override
    public void initialize(ValidGalleryCategory constraintAnnotation) {
    	enumValues = EnumSet.allOf(GalleryCategory.class).stream()
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Set<String> value, ConstraintValidatorContext context) {
        return value == null || enumValues.containsAll(value);
    }

}
