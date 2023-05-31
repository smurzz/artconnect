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
}
