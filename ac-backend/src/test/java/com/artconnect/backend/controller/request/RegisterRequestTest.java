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


}
