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

}
