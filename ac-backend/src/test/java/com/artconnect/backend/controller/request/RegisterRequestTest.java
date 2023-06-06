package com.artconnect.backend.controller.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



public class RegisterRequestTest {


    @Test
    public void testValidRegisterRequest() {
        RegisterRequest request = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        Assertions.assertEquals("John", request.getFirstname());
        Assertions.assertEquals("Doe", request.getLastname());
        Assertions.assertEquals("john.doe@example.com", request.getEmail());
        Assertions.assertEquals("password123", request.getPassword());
    }




    @Test
    public void testRegisterRequestFirstname() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("John")
                .build();

        Assertions.assertEquals("John", registerRequest.getFirstname());
    }


    @Test
    public void testRegisterRequestLastname() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .lastname("Doe")
                .build();

        Assertions.assertEquals("Doe", registerRequest.getLastname());
    }


    @Test
    public void testRegisterRequestEmail() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("john.doe@example.com")
                .build();

        Assertions.assertEquals("john.doe@example.com", registerRequest.getEmail());
    }

    
    @Test
    public void testRegisterRequestPassword() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .password("password123")
                .build();

        Assertions.assertEquals("password123", registerRequest.getPassword());
    }


    @Test
    public void testRegisterRequestSetter() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstname("John");
        registerRequest.setLastname("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password123");

        Assertions.assertEquals("John", registerRequest.getFirstname());
        Assertions.assertEquals("Doe", registerRequest.getLastname());
        Assertions.assertEquals("john.doe@example.com", registerRequest.getEmail());
        Assertions.assertEquals("password123", registerRequest.getPassword());
    }

    @Test
    public void testRegisterRequestEquals() {
        RegisterRequest request1 = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        RegisterRequest request2 = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        Assertions.assertEquals(request1, request2);
    }

    @Test
    public void testRegisterRequestHashCode() {
        RegisterRequest request1 = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        RegisterRequest request2 = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        Assertions.assertEquals(request1.hashCode(), request2.hashCode());
    }
    @Test
    public void testRegisterRequestToString() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("John")
                .lastname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        String expectedToString = "RegisterRequest(firstname=John, lastname=Doe, email=john.doe@example.com, password=password123)";
        Assertions.assertEquals(expectedToString, registerRequest.toString());
    }

    @Test
    public void testRegisterRequestNoArgsConstructor() {
        RegisterRequest registerRequest = new RegisterRequest();

        Assertions.assertNull(registerRequest.getFirstname());
        Assertions.assertNull(registerRequest.getLastname());
        Assertions.assertNull(registerRequest.getEmail());
        Assertions.assertNull(registerRequest.getPassword());
    }
}
