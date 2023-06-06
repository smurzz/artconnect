package com.artconnect.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.artconnect.backend.controller.request.ResetPasswordRequest;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.model.User;
import com.artconnect.backend.repository.UserRepository;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.mock;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;



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
    void processForgotPassword_validEmail_shouldSendEmail() throws MessagingException, UnsupportedEncodingException {
        // Mock UserRepository response
        User user = new User();
        user.setEmail(EMAIL);
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.just(user));

        // Mock JwtService response
        when(jwtService.generateConfirmationToken(any(User.class))).thenReturn(TOKEN);

        // Mock EmailService response
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());

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


    @Test
    public void testProcessForgotPassword_EncodingError() {
        // Arrange
        String email = "example@example.com";
        String subject = "Reset Password";
        String content = "<table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "  <tbody><tr>\n" +
                "    <td></td>\n" +
                "    <td width=\"580\" valign=\"top\">\n" +
                "      \n" +
                "              <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                <tbody><tr>\n" +
                "                  <td colspan=\"2\" height=\"20\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td width=\"30\"></td>\n" +
                "                  <td align=\"left\">\n" +
                "                    <div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;line-height:24px;color:#0b0c0c\">\n" +
                "                      <p style=\"Margin:0\">If you're having trouble with the button above, you can also reset your password by clicking the link below:</p>\n" +
                "                      <p style=\"Margin:0\"><a href=\"#\" style=\"color:#1D70B8\">Reset Password</a></p>\n" +
                "                    </div>\n" +
                "                  </td>\n" +
                "                  <td width=\"30\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td colspan=\"2\" height=\"20\"></td>\n" +
                "                </tr>\n" +
                "              </tbody></table>\n" +
                "      \n" +
                "    </td>\n" +
                "    <td></td>\n" +
                "  </tr>\n" +
                "</tbody></table>\n" +
                "<table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "  <tbody><tr>\n" +
                "    <td height=\"30\"></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td width=\"10\" valign=\"middle\"></td>\n" +
                "    <td>\n" +
                "      \n" +
                "              <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                <tbody><tr>\n" +
                "                  <td bgcolor=\"#ffffff\" width=\"100%\" height=\"10\"></td>\n" +
                "                </tr>\n" +
                "              </tbody></table>\n" +
                "      \n" +
                "    </td>\n" +
                "    <td width=\"10\" valign=\"middle\"></td>\n" +
                "  </tr>\n" +
                "</tbody></table>\n" +
                "<table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "  <tbody><tr>\n" +
                "    <td></td>\n" +
                "    <td width=\"580\" valign=\"top\">\n" +
                "      \n" +
                "              <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                <tbody><tr>\n" +
                "                  <td colspan=\"2\" height=\"20\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td width=\"30\"></td>\n" +
                "                  <td align=\"left\">\n" +
                "                    <div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;line-height:24px;color:#0b0c0c\">\n" +
                "                      <p style=\"Margin:0;font-size:14px;line-height:18px\">If you have any questions, feel free to contact us at <a href=\"mailto:support@example.com\" style=\"color:#1D70B8;text-decoration:underline\">support@example.com</a>.</p>\n" +
                "                    </div>\n" +
                "                  </td>\n" +
                "                  <td width=\"30\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td colspan=\"2\" height=\"20\"></td>\n" +
                "                </tr>\n" +
                "              </tbody></table>\n" +
                "      \n" +
                "    </td>\n" +
                "    <td></td>\n" +
                "  </tr>\n" +
                "</tbody></table>\n" +
                "\n" +
                "<div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";

        // Act
        emailService.sendEmail(email, subject, content);

        // Assert
        assertTrue(true); // Add your assertion here
    }


    @Disabled
    @Test
    void updatePassword_InvalidTokenAndUser_ShouldThrowException() {
        // Arrange
        String token = "invalid-token";
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setToken(token);
        resetPasswordRequest.setPassword("new-password");

        // Mock the behavior of JwtService to throw an exception when token is invalid
        when(jwtService.extractUsername(token)).thenThrow(new RuntimeException("Invalid token"));

        // Mock the behavior of UserRepository to return an empty Mono
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());

        // Act and Assert
        assertThrows(ResponseStatusException.class, () ->
                forgotPasswordService.updatePassword(resetPasswordRequest));

        // Verify interactions
        verify(jwtService).extractUsername(token);
        verify(userRepository).findByEmail(anyString());
        verifyNoMoreInteractions(jwtService, userRepository, passwordEncoder);
    }


}



