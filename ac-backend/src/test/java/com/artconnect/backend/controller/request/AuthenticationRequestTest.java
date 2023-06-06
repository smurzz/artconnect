package com.artconnect.backend.controller.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;




public class AuthenticationRequestTest {


    
    @Test
    public void testConstructorAndGetters() {
        String email = "test@example.com";
        String password = "password123";

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);

        Assertions.assertEquals(email, authenticationRequest.getEmail());
        Assertions.assertEquals(password, authenticationRequest.getPassword());
    }

    @Test
    public void testSetterAndGetters() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        String email = "test@example.com";
        String password = "password123";

        authenticationRequest.setEmail(email);
        authenticationRequest.setPassword(password);

        Assertions.assertEquals(email, authenticationRequest.getEmail());
        Assertions.assertEquals(password, authenticationRequest.getPassword());
    }


    @Test
    public void testAuthenticationRequestEmail() {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("john.doe@example.com")
                .build();

        Assertions.assertEquals("john.doe@example.com", authenticationRequest.getEmail());
    }


    @Test
    public void testAuthenticationRequestPassword() {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .password("password123")
                .build();

        Assertions.assertEquals("password123", authenticationRequest.getPassword());
    }

    @Test
    public void testDataMethods() {
        String email = "test@example.com";
        String password = "password123";

        AuthenticationRequest authenticationRequest1 = new AuthenticationRequest(email, password);
        AuthenticationRequest authenticationRequest2 = new AuthenticationRequest(email, password);

        // Test equals() method
        Assertions.assertEquals(authenticationRequest1, authenticationRequest2);

        // Test hashCode() method
        Assertions.assertEquals(authenticationRequest1.hashCode(), authenticationRequest2.hashCode());

        // Test toString() method
        String expectedToString = "AuthenticationRequest(email=test@example.com, password=password123)";
        Assertions.assertEquals(expectedToString, authenticationRequest1.toString());
    }

    @Test
    public void testEqualsWithDifferentObjects() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test@example.com", "password123");
        String differentObject = "This is a different object";

        Assertions.assertNotEquals(authenticationRequest, differentObject);
    }

    @Test
    public void testEqualsWithNull() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test@example.com", "password123");

        Assertions.assertNotEquals(authenticationRequest, null);
    }

    @Test
    public void testHashCodeSameObject() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test@example.com", "password123");

        Assertions.assertEquals(authenticationRequest.hashCode(), authenticationRequest.hashCode());
    }
    @Test
    public void testHashCodeDifferentObjects() {
        AuthenticationRequest authenticationRequest1 = new AuthenticationRequest("test@example.com", "password123");
        AuthenticationRequest authenticationRequest2 = new AuthenticationRequest("test@example.com", "password123");

        Assertions.assertEquals(authenticationRequest1.hashCode(), authenticationRequest2.hashCode());
    }

    @Test
    public void testToStringWithPopulatedObject() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test@example.com", "password123");

        String expectedToString = "AuthenticationRequest(email=test@example.com, password=password123)";
        Assertions.assertEquals(expectedToString, authenticationRequest.toString());
    }

    @Test
    public void testToStringWithEmptyObject() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        String expectedToString = "AuthenticationRequest(email=null, password=null)";
        Assertions.assertEquals(expectedToString, authenticationRequest.toString());
    }

    @Test
    public void testToString() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail(email);
        request.setPassword(password);

        // Act
        String toString = request.toString();

        // Assert
        Assertions.assertTrue(toString.contains("email=" + email));
        Assertions.assertTrue(toString.contains("password=" + password));
    }

    @Test
    public void testEquals() {
        // Arrange
        AuthenticationRequest request1 = new AuthenticationRequest();
        request1.setEmail("test@example.com");
        request1.setPassword("password");

        AuthenticationRequest request2 = new AuthenticationRequest();
        request2.setEmail("test@example.com");
        request2.setPassword("password");

        AuthenticationRequest request3 = new AuthenticationRequest();
        request3.setEmail("test2@example.com");
        request3.setPassword("password");

        // Assert
        Assertions.assertEquals(request1, request2); // Objects with the same values should be equal
        Assertions.assertNotEquals(request1, request3); // Objects with different values should not be equal
        Assertions.assertNotEquals(request2, null); // Object should not be equal to null
    }

    @Test
    public void testNoArgsConstructor() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();

        // Verify that all fields are initialized to their default values
        Assertions.assertNull(authenticationRequest.getEmail());
        Assertions.assertNull(authenticationRequest.getPassword());
    }

    @Test
    public void testAllArgsConstructor() {
        String email = "test@example.com";
        String password = "password123";

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);

        // Verify that the fields are initialized with the provided values
        Assertions.assertEquals(email, authenticationRequest.getEmail());
        Assertions.assertEquals(password, authenticationRequest.getPassword());
    }

    @Test
    public void testBuilder() {
        String email = "test@example.com";
        String password = "password123";

        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email(email)
                .password(password)
                .build();

        // Verify that the fields are initialized with the provided values using the builder
        Assertions.assertEquals(email, authenticationRequest.getEmail());
        Assertions.assertEquals(password, authenticationRequest.getPassword());
    }

    @Test
    public void testSetterWithNonNullValue() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        String email = "test@example.com";

        authenticationRequest.setEmail(email);

        // Verify that the email field is set to the provided value
        Assertions.assertEquals(email, authenticationRequest.getEmail());
    }

    @Test
    public void testSetterWithNullValue() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        String password = "password123";

        authenticationRequest.setPassword(password);

        // Verify that the password field is set to the provided value
        Assertions.assertEquals(password, authenticationRequest.getPassword());
    }

    @Test
    public void testGetterWithNonNullValue() {
        String email = "test@example.com";
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, "password123");

        // Verify that the getEmail() method returns the correct value
        Assertions.assertEquals(email, authenticationRequest.getEmail());
    }

    @Test
    public void testGetterWithNullValue() {
        String password = "password123";
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("test@example.com", password);

        // Verify that the getPassword() method returns the correct value
        Assertions.assertEquals(password, authenticationRequest.getPassword());
    }

}
