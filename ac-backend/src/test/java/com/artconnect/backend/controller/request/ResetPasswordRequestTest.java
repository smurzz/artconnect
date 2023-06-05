package com.artconnect.backend.controller.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;

public class ResetPasswordRequestTest {

    @Test
    void setPassword_ValidPassword_Success() {
        // Arrange
        String validPassword = "testPassword";
        ResetPasswordRequest request = new ResetPasswordRequest();

        // Act
        request.setPassword(validPassword);

        // Assert
        Assertions.assertEquals(validPassword, request.getPassword());
    }

    @Test
    public void testResetPasswordRequest() {
     
        ResetPasswordRequest request = new ResetPasswordRequest("newPassword", null);

        // Verify that the password is set correctly
        Assertions.assertEquals("newPassword", request.getPassword());
    }
   
    @Test
    public void testResetPasswordRequest2() {
        // Create a ResetPasswordRequest object
        ResetPasswordRequest request = new ResetPasswordRequest();

        // Set the token
        request.setToken("abcd1234");

        // Verify that the token is set correctly
        Assertions.assertEquals("abcd1234", request.getToken());
    }




    @Test
    public void testResetPasswordRequestWithValidPassword() {
        // Create a valid ResetPasswordRequest object
        ResetPasswordRequest request = new ResetPasswordRequest("newPassword", null);

        // Verify that the password is set correctly
        Assertions.assertEquals("newPassword", request.getPassword());
    }

    @Test
    public void testResetPasswordRequestWithInvalidPassword() {
        // Create a ResetPasswordRequest object with an invalid password (less than 3 characters)
        ResetPasswordRequest request = new ResetPasswordRequest("pw", null);

        // Validate the object using Hibernate Validator
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ResetPasswordRequest>> violations = validator.validate(request);

        // Verify that the password constraint violation is detected
        Assertions.assertFalse(violations.isEmpty(), "Expected password constraint violation");

        // Alternatively, if you want to specifically check for ConstraintViolationException:
        // Assertions.assertThrows(ConstraintViolationException.class, () -> validator.validate(request));
    }


}
