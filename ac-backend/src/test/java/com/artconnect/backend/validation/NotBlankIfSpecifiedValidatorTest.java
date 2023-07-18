package com.artconnect.backend.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotBlankIfSpecifiedValidatorTest {

    @Test
    void testIsValidWithNullValue() {
        NotBlankIfSpecifiedValidator validator = new NotBlankIfSpecifiedValidator();
        assertTrue(validator.isValid(null, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void testIsValidWithEmptyValue() {
        NotBlankIfSpecifiedValidator validator = new NotBlankIfSpecifiedValidator();
        assertFalse(validator.isValid("", mock(ConstraintValidatorContext.class)));
    }

    @Test
    void testIsValidWithBlankValue() {
        NotBlankIfSpecifiedValidator validator = new NotBlankIfSpecifiedValidator();
        assertFalse(validator.isValid("   ", mock(ConstraintValidatorContext.class)));
    }

    @Test
    void testIsValidWithNonEmptyValue() {
        NotBlankIfSpecifiedValidator validator = new NotBlankIfSpecifiedValidator();
        assertTrue(validator.isValid("some value", mock(ConstraintValidatorContext.class)));
    }

    @Test
    void testInitialize() {
        NotBlankIfSpecifiedValidator validator = new NotBlankIfSpecifiedValidator();
        assertDoesNotThrow(() -> validator.initialize(null));
    }

}
