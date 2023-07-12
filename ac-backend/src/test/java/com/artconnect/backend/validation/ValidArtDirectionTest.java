package com.artconnect.backend.validation;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class ValidArtDirectionTest {

    private EnumStringArtDirectionValidator validator;

    @BeforeEach
    void setup() {
        validator = new EnumStringArtDirectionValidator();
        validator.initialize(null);
    }

    @Test
    void testIsValidWithNullValue() {
        assertTrue(validator.isValid(null, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void testIsValidWithEmptyValue() {
        assertTrue(validator.isValid(new HashSet<>(), mock(ConstraintValidatorContext.class)));
    }

    @Test
    void testIsValidWithValidValues() throws NoSuchFieldException, IllegalAccessException {
        Set<String> values = new HashSet<>();
        values.add("LANDSCAPE");
        values.add("PORTRAIT");

        EnumStringArtDirectionValidator validator = new EnumStringArtDirectionValidator();

        Field enumValuesField = EnumStringArtDirectionValidator.class.getDeclaredField("enumValues");
        enumValuesField.setAccessible(true);
        enumValuesField.set(validator, Set.of("LANDSCAPE", "PORTRAIT"));

        assertTrue(validator.isValid(values, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void testIsValidWithInvalidValue() {
        Set<String> values = new HashSet<>();
        values.add("LANDSCAPE");
        values.add("INVALID_DIRECTION");
        assertFalse(validator.isValid(values, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void testIsValidWithMixedValidAndInvalidValues() {
        Set<String> values = new HashSet<>();
        values.add("LANDSCAPE");
        values.add("PORTRAIT");
        values.add("INVALID_DIRECTION");
        assertFalse(validator.isValid(values, mock(ConstraintValidatorContext.class)));
    }
}

