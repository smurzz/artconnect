package com.artconnect.backend.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.Year;


public class MaxYearValidatorTest {
    @Test
    void testIsValidWithValidYear() {
        MaxYearValidator validator = new MaxYearValidator();
        assertTrue(validator.isValid(2022, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void testIsValidWithNullYear() {
        MaxYearValidator validator = new MaxYearValidator();
        assertTrue(validator.isValid(null, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void testIsValidWithCurrentYear() {
        MaxYearValidator validator = new MaxYearValidator();
        int currentYear = Year.now().getValue();
        assertTrue(validator.isValid(currentYear, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void testIsValidWithYearGreaterThanCurrentYear() {
        MaxYearValidator validator = new MaxYearValidator();
        int currentYear = Year.now().getValue();
        assertFalse(validator.isValid(currentYear + 1, mock(ConstraintValidatorContext.class)));
    }
}