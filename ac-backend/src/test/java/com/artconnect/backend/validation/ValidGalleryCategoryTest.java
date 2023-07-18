package com.artconnect.backend.validation;


import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class ValidGalleryCategoryTest {

    @Test
    void testIsValidWithNullValue() {
        EnumStringValidator validator = new EnumStringValidator();
        validator.initialize(validGalleryCategoryAnnotation());
        assertTrue(validator.isValid(null, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void testIsValidWithValidValues() {
        EnumStringValidator validator = new EnumStringValidator();
        validator.initialize(validGalleryCategoryAnnotation());
        Set<String> values = new HashSet<>();
        values.add("PRINT");
        values.add("PAINTING");
        assertTrue(validator.isValid(values, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void testIsValidWithInvalidValue() {
        EnumStringValidator validator = new EnumStringValidator();
        validator.initialize(validGalleryCategoryAnnotation());
        Set<String> values = new HashSet<>();
        values.add("INVALID_CATEGORY");
        assertFalse(validator.isValid(values, mock(ConstraintValidatorContext.class)));
    }

    private ValidGalleryCategory validGalleryCategoryAnnotation() {
        return new ValidGalleryCategory() {
            @Override
            public String message() {
                return "Invalid gallery category";
            }

            @Override
            public Class<?>[] groups() {
                return new Class<?>[0];
            }

            @Override
            public Class<? extends javax.validation.Payload>[] payload() {
                return new Class[0];
            }

            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return ValidGalleryCategory.class;
            }
        };
    }
}