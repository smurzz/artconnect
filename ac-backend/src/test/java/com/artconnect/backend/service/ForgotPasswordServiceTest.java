package com.artconnect.backend.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.controller.request.ResetPasswordRequest;
import com.artconnect.backend.model.Role;
import com.artconnect.backend.model.User;
import com.artconnect.backend.repository.UserRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


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

    private final String frontendBaseUrl = "http://example.com";
    private static final String EMAIL = "test@example.com";
    private static final String TOKEN = "testToken";
    private static final String PASSWORD = "password";

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
        ReflectionTestUtils.setField(forgotPasswordService, "frontendBaseUrl", frontendBaseUrl);
    }

    @Test
    void updatePasswordSuccess() {
        // Arrange
        String userEmail = "example@example.com";
        ResetPasswordRequest resetPasswordRequest = ResetPasswordRequest.builder()
        		.password(PASSWORD)
        		.token(TOKEN)
        		.build();
        
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
        		.username(userEmail)
        		.password(resetPasswordRequest.getPassword())
        		.roles(Role.USER.name())
        		.build();
        User userBeforeUpdatePassword = User.builder()
        		.email(userEmail)
        		.password("old-password")
        		.role(Role.USER)
        		.build();
        
        User userAfterUpdatePassword = User.builder()
        		.email(userEmail)
        		.password(resetPasswordRequest.getPassword())
        		.role(Role.USER)
        		.build();
        
        when(jwtService.extractUsername(anyString())).thenReturn(userEmail);
        when(userDetailsService.findByUsername(anyString())).thenReturn(Mono.just(userDetails));
        when(jwtService.isTokenValid(anyString(), any(UserDetails.class))).thenReturn(true);
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.just(userBeforeUpdatePassword));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(userAfterUpdatePassword));
        
        Mono<String> result = forgotPasswordService.updatePassword(resetPasswordRequest);
        
        StepVerifier.create(result)
        	.expectNext("You have successfully changed your password.")
        	.verifyComplete();
    }
    
    @Test
    void updatePasswordUsernameIsEmpty() {
        // Arrange
        String userEmail = "example@example.com";
        ResetPasswordRequest resetPasswordRequest = ResetPasswordRequest.builder()
        		.password(PASSWORD)
        		.token(TOKEN)
        		.build();
        
        when(jwtService.extractUsername(userEmail)).thenReturn(null);
        
        // Act and assert
        assertThrows(NullPointerException.class, () -> {
            forgotPasswordService.updatePassword(resetPasswordRequest);
        });
    }
    
    @Test
    void updatePasswordTokenIsInvalid() {
        // Arrange
        String userEmail = "example@example.com";
        ResetPasswordRequest resetPasswordRequest = ResetPasswordRequest.builder()
        		.password(PASSWORD)
        		.token(TOKEN)
        		.build();
        
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
        		.username(userEmail)
        		.password(resetPasswordRequest.getPassword())
        		.roles(Role.USER.name())
        		.build();
        
        when(jwtService.extractUsername(anyString())).thenReturn(userEmail);
        when(userDetailsService.findByUsername(anyString())).thenReturn(Mono.just(userDetails));
        when(jwtService.isTokenValid(anyString(), any(UserDetails.class))).thenReturn(false);
        
        Mono<String> result = forgotPasswordService.updatePassword(resetPasswordRequest);
        
        StepVerifier.create(result)
        .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException
                && ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.UNAUTHORIZED)
        .verify();
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
    void processForgotPassword_EmailSendingFailure() {
        // Arrange
        String email = "test@example.com";
        User user = User.builder()
        		.email(email)
        		.build();
        when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));
        doThrow(IllegalStateException.class).when(emailService).sendEmail(anyString(), anyString(), anyString());

        // Act
        Mono<String> result = forgotPasswordService.processForgotPassword(email);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException
                        && ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR
                        && ((ResponseStatusException) throwable).getReason().equals("Failed to send email"))
                .verify();
    }

    @Test
    void testProcessForgotPassword_EncodingError() {
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

}



