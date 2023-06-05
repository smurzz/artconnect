package com.artconnect.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmbee.mms.localuser.security.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.controller.request.ResetPasswordRequest;
import com.artconnect.backend.model.User;
import com.artconnect.backend.repository.UserRepository;
import com.mongodb.client.model.Field;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
        String token = "token";
        String userEmail = "userEmail";
        String password = "password";
        ResetPasswordRequest request = new ResetPasswordRequest(token, password);
    
        when(jwtService.extractUsername(token)).thenReturn(userEmail);
        when(userDetailsService.findByUsername(userEmail)).thenReturn(Mono.just(new UserDetailsImpl(null)));
        doNothing().when(jwtService).isTokenValid(eq(token), any(UserDetailsImpl.class));
        when(userRepository.findByEmail(userEmail)).thenReturn(Mono.just(new User()));
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(Mono.just(new User()));
    
        Mono<String> result = forgotPasswordService.updatePassword(request);
    
        assertEquals("You have successfully changed your password.", result.block());
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

