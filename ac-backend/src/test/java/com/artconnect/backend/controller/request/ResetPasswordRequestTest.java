package com.artconnect.backend.controller.request;

import jakarta.validation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

    @Test
    public void testBuilder() {
        // Arrange
        String password = "testPassword";
        String token = "testToken";

        // Act
        ResetPasswordRequest request = ResetPasswordRequest.builder()
                .password(password)
                .token(token)
                .build();

        // Assert
        Assertions.assertEquals(password, request.getPassword());
        Assertions.assertEquals(token, request.getToken());
    }
    @Test
    public void testBuilderWithDefaultValues() {
        // Arrange
        String defaultPassword = null;
        String defaultToken = null;

        // Act
        ResetPasswordRequest request = ResetPasswordRequest.builder().build();

        // Assert
        Assertions.assertEquals(defaultPassword, request.getPassword());
        Assertions.assertEquals(defaultToken, request.getToken());
    }

    @Test
    public void testBuilderWithOnlyPassword() {
        // Arrange
        String password = "testPassword";
        String defaultToken = null;

        // Act
        ResetPasswordRequest request = ResetPasswordRequest.builder()
                .password(password)
                .build();

        // Assert
        Assertions.assertEquals(password, request.getPassword());
        Assertions.assertEquals(defaultToken, request.getToken());
    }

    @Test
    public void testBuilderWithOnlyToken() {
        // Arrange
        String defaultPassword = null;
        String token = "testToken";

        // Act
        ResetPasswordRequest request = ResetPasswordRequest.builder()
                .token(token)
                .build();

        // Assert
        Assertions.assertEquals(defaultPassword, request.getPassword());
        Assertions.assertEquals(token, request.getToken());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        ResetPasswordRequest request1 = new ResetPasswordRequest("password", "token");
        ResetPasswordRequest request2 = new ResetPasswordRequest("password", "token");
        ResetPasswordRequest request3 = new ResetPasswordRequest("password123", "token123");

        // Assert
        Assertions.assertEquals(request1, request2); // Equal objects
        Assertions.assertNotEquals(request1, request3); // Non-equal objects
        Assertions.assertEquals(request1.hashCode(), request2.hashCode()); // Hash codes of equal objects should match
    }

    @Test
    public void testToString() {
        // Arrange
        ResetPasswordRequest request = new ResetPasswordRequest("password", "token");
        String expectedToString = "ResetPasswordRequest(password=password, token=token)";

        // Assert
        Assertions.assertEquals(expectedToString, request.toString());
    }

    @Test
    public void testSetterAndGetters() {
        // Arrange
        String password = "testPassword";
        String token = "testToken";

        // Act
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setPassword(password);
        request.setToken(token);

        // Assert
        Assertions.assertEquals(password, request.getPassword());
        Assertions.assertEquals(token, request.getToken());
    }

    @Test
    public void testNoArgsConstructor() {
        // Arrange
        ResetPasswordRequest request = new ResetPasswordRequest();

        // Assert
        Assertions.assertNull(request.getPassword());
        Assertions.assertNull(request.getToken());
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange
        String password = "testPassword";
        String token = "testToken";

        // Act
        ResetPasswordRequest request = new ResetPasswordRequest(password, token);

        // Assert
        Assertions.assertEquals(password, request.getPassword());
        Assertions.assertEquals(token, request.getToken());
    }

    @Test
    public void testParameterizedConstructor() {
        // Arrange
        String password = "testPassword";
        String token = "testToken";

        // Act
        ResetPasswordRequest request = new ResetPasswordRequest(password, token);

        // Assert
        Assertions.assertEquals(password, request.getPassword());
        Assertions.assertEquals(token, request.getToken());
    }

    @Test
    public void testEqualsAndHashCode_DifferentClass_ReturnsFalse() {
        // Arrange
        ResetPasswordRequest request = new ResetPasswordRequest();
        String differentClass = "testString";

        // Assert
        Assertions.assertNotEquals(request, differentClass);
    }

    @Test
    public void testEqualsAndHashCode_SameObject_ReturnsTrue() {
        // Arrange
        ResetPasswordRequest request = new ResetPasswordRequest();

        // Assert
        Assertions.assertEquals(request, request);
    }

    @Test
    public void testEqualsAndHashCode_NullObject_ReturnsFalse() {
        // Arrange
        ResetPasswordRequest request = new ResetPasswordRequest();

        // Assert
        Assertions.assertNotEquals(request, null);
    }

    @Test
    public void testSetToken_ValidToken_Success() {
        // Arrange
        String validToken = "testToken";
        ResetPasswordRequest request = new ResetPasswordRequest();

        // Act
        request.setToken(validToken);

        // Assert
        Assertions.assertEquals(validToken, request.getToken());
    }

    @Test
    public void testSetToken_NullToken_Success() {
        // Arrange
        ResetPasswordRequest request = new ResetPasswordRequest();

        // Act
        request.setToken(null);

        // Assert
        Assertions.assertNull(request.getToken());
    }

    @Test
    public void testInvalidPassword() {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setPassword("ab");
        Assertions.assertThrows(ConstraintViolationException.class, () -> validate(request));
    }

    // Helper method to validate the ResetPasswordRequest using javax.validation.Validator
    private void validate(ResetPasswordRequest request) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ResetPasswordRequest>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }


}
