package com.artconnect.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.artconnect.backend.controller.request.ResetPasswordRequest;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.model.User;
import com.artconnect.backend.repository.UserRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Constructor;
import jakarta.validation.constraints.Size;
//import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;

class ForgotPasswordServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private ReactiveUserDetailsService userDetailsService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ForgotPasswordService forgotPasswordService;


    private static final String EMAIL = "test@example.com";
    private static final String TOKEN = "testToken";
    private static final String FRONTEND_BASE_URL = "http://example.com";


    @BeforeEach
    void setup() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        forgotPasswordService = new ForgotPasswordService(
                userRepository,
                passwordEncoder,
                jwtService,
                userDetailsService,
                emailService
        );

        // Set frontendBaseUrl using reflection
        java.lang.reflect.Field frontendBaseUrlField = ForgotPasswordService.class.getDeclaredField("frontendBaseUrl");
        frontendBaseUrlField.setAccessible(true);
        frontendBaseUrlField.set(forgotPasswordService, FRONTEND_BASE_URL);

    }


    @Test
    void updatePassword() {
        // Arrange
        String userEmail = "example@example.com";
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest() {
            public String getEmail() {
                return userEmail;
            }
        };

        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        JwtService jwtService = mock(JwtService.class);
        ReactiveUserDetailsService userDetailsService = mock(ReactiveUserDetailsService.class);
        EmailService emailService = mock(EmailService.class);

        ForgotPasswordService forgotPasswordService = new ForgotPasswordService(userRepository, passwordEncoder, jwtService, userDetailsService, emailService);

        // Act and assert
        assertThrows(NullPointerException.class, () -> {
            forgotPasswordService.updatePassword(resetPasswordRequest);
        });
    }


    @Test
    void processForgotPassword_validEmail_shouldSendEmail() {
        // Mock UserRepository response
        User user = new User();
        user.setEmail(EMAIL);
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.just(user));

        // Mock JwtService response
        when(jwtService.generateConfirmationToken(any(User.class))).thenReturn(TOKEN);

        // Mock EmailService response
       // when(emailService.sendEmail(anyString(), anyString(), anyString())).thenReturn(Mono.empty());      error here

        // Call the method and verify the response
        StepVerifier.create(forgotPasswordService.processForgotPassword(EMAIL))
                .expectNext("We have sent a reset password link to your email. Please check.")
                .verifyComplete();

        // Verify the interactions
        verify(userRepository).findByEmail(eq(EMAIL));
        verify(jwtService).generateConfirmationToken(eq(user));
        verify(emailService).sendEmail(eq(EMAIL), anyString(), anyString());
    }

    @Test
    void processForgotPassword_invalidEmail_shouldReturnNotFoundError() {
        // Mock UserRepository response
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());

        // Call the method and verify the error response
        StepVerifier.create(forgotPasswordService.processForgotPassword(EMAIL))
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                        ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.NOT_FOUND)
                .verify();

        // Verify the interactions
        verify(userRepository).findByEmail(eq(EMAIL));
        verifyNoMoreInteractions(jwtService, emailService);
    }

    @Test
    void processForgotPassword_emptyEmail_shouldReturnBadRequestError() {
        // Call the method and verify the error response
        StepVerifier.create(forgotPasswordService.processForgotPassword(""))
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                        ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.BAD_REQUEST)
                .verify();

        // Verify no interactions occurred
        verifyNoInteractions(userRepository, jwtService, emailService);
    }


}



