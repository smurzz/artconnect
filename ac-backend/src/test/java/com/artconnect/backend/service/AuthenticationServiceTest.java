package com.artconnect.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.controller.request.AuthenticationRequest;
import com.artconnect.backend.controller.request.RegisterRequest;
import com.artconnect.backend.controller.response.AuthenticationResponse;
import com.artconnect.backend.model.user.Role;
import com.artconnect.backend.model.user.Status;
import com.artconnect.backend.model.user.User;
import com.artconnect.backend.repository.UserRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


//NEED TO BE CORRECTED (line 260 and line 279)

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private ReactiveAuthenticationManager authenticationManager;

    @Mock
    private ReactiveUserDetailsService userDetailsService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        authenticationService = new AuthenticationService(
                userRepository,
                passwordEncoder,
                jwtService,
                authenticationManager,
                userDetailsService,
                emailService
        );
    }

    @Test
    void testRegister() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password");

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(new Date(0))
                .isAccountEnabled(Status.RESTRICTED)
                .role(Role.USER)
                .build();

        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));
        when(jwtService.generateConfirmationToken(any(User.class))).thenReturn("token");

        Mono<String> result = authenticationService.register(request);

        StepVerifier.create(result)
                .expectNext("Verify email by the link sent on your email address")
                .verifyComplete();
    }
    
    @Test
    void testRegisterError() {
        RegisterRequest request = new RegisterRequest();
        request.setFirstname("John");
        request.setLastname("Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password");

        when(userRepository.save(any(User.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send email")));
       
        Mono<String> result = authenticationService.register(request);

        StepVerifier.create(result)
	        .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException
	                && ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR
	                && ((ResponseStatusException) throwable).getReason().equals("Failed to send email"))
	        .verify();
    }
    
    @Test
    void testConfirmEmail() {
        String confirmToken = "testToken";
        String userEmail = "test@example.com";
        UserDetails userDetails = mock(UserDetails.class);
        User user = mock(User.class);

        when(jwtService.extractUsername(confirmToken)).thenReturn(userEmail);
        when(userDetailsService.findByUsername(userEmail)).thenReturn(Mono.just(userDetails));
        when(jwtService.isTokenValid(confirmToken, userDetails)).thenReturn(true);
        when(userRepository.findByEmail(userEmail)).thenReturn(Mono.just(user));
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        String expectedResult = "<html lang=\"en\">\r\n"
				+ "<head>\r\n"
				+ "	<meta charset=\"utf-8\" />\r\n"
				+ "	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\" />\r\n"
				+ "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
				+ "	<title></title>\r\n"
				+ "	<link href='https://fonts.googleapis.com/css?family=Lato:300,400|Montserrat:700' rel='stylesheet' type='text/css'>\r\n"
				+ "	<style>\r\n"
				+ "		@import url(//cdnjs.cloudflare.com/ajax/libs/normalize/3.0.1/normalize.min.css);\r\n"
				+ "		@import url(//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css);\r\n"
				+ "	</style>\r\n"
				+ "	<link rel=\"stylesheet\" href=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/default_thank_you.css\">\r\n"
				+ "	<script src=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/jquery-1.9.1.min.js\"></script>\r\n"
				+ "	<script src=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/html5shiv.js\"></script>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "	<header class=\"site-header\" id=\"header\">\r\n"
				+ "		<h1 class=\"site-header__title\" data-lead-id=\"site-header-title\">THANK YOU!</h1>\r\n"
				+ "	</header>\r\n"
				+ "\r\n"
				+ "	<div class=\"main-content\">\r\n"
				+ "		<i class=\"fa fa-check main-content__checkmark\" id=\"checkmark\"></i>\r\n"
				+ "		<p class=\"main-content__body\" data-lead-id=\"main-content-body\">Congratulations! Your registration on ArtConnect platform has been confirmed successfully. You can now log in to your account and start exploring our community of artists and art lovers. Thank you for joining us and we wish you an exciting and creative experience!</p>\r\n"
				+ "	</div>\r\n"
				+ "\r\n"
				+ "	<footer class=\"site-footer\" id=\"footer\">\r\n"
				+ "		<p class=\"site-footer__fineprint\" id=\"fineprint\">Copyright ©2014 | All Rights Reserved</p>\r\n"
				+ "	</footer>\r\n"
				+ "</body>\r\n"
				+ "</html>";

        String result = authenticationService.confirmEmail(confirmToken).block();
        assertEquals(expectedResult, result);
    }
    
    @Test
    void testConfirmEmailInvalidToken() {
        String confirmToken = "testToken";
        String userEmail = "test@example.com";
        UserDetails userDetails = mock(UserDetails.class);
        String expectedAnswer = failedConfirmEmail();
        
        when(jwtService.extractUsername(confirmToken)).thenReturn(userEmail);
        when(userDetailsService.findByUsername(userEmail)).thenReturn(Mono.just(userDetails));
        when(jwtService.isTokenValid(confirmToken, userDetails)).thenReturn(false);
        
        Mono<String> result = authenticationService.confirmEmail(confirmToken);
        
        StepVerifier.create(result)
        	.expectNext(expectedAnswer)
        	.verifyComplete();

    }
    
    @Test
    void testConfirmEmailSignatureException() {
        String confirmToken = "testToken";
        String expectedAnswer = failedConfirmEmail();
        
        when(jwtService.extractUsername(confirmToken)).thenThrow(SignatureException.class);
        
        Mono<String> result = authenticationService.confirmEmail(confirmToken);
        
        StepVerifier.create(result)
        	.expectNext(expectedAnswer)
        	.verifyComplete();
    }
    
    @Test
    void testConfirmEmailMalformedJwtException() {
        String confirmToken = "testToken";
        String expectedAnswer = failedConfirmEmail();
        
        when(jwtService.extractUsername(confirmToken)).thenThrow(MalformedJwtException.class);
        
        Mono<String> result = authenticationService.confirmEmail(confirmToken);
        
        StepVerifier.create(result)
        	.expectNext(expectedAnswer)
        	.verifyComplete();
    }
    
    @Test
    void testConfirmEmailIllegalArgumentException() {
        String confirmToken = "testToken";
        String expectedAnswer = failedConfirmEmail();
        
        when(jwtService.extractUsername(confirmToken)).thenThrow(IllegalArgumentException.class);
        
        Mono<String> result = authenticationService.confirmEmail(confirmToken);
        
        StepVerifier.create(result)
        	.expectNext(expectedAnswer)
        	.verifyComplete();
    }
    
    @Test
    void testConfirmEmailExpiredToken() {
        String confirmToken = "testToken";
        String expectedAnswer = failedConfirmEmail();
        
        when(jwtService.extractUsername(confirmToken)).thenThrow(ExpiredJwtException.class);
        
        Mono<String> result = authenticationService.confirmEmail(confirmToken);
        
        StepVerifier.create(result)
        	.expectNext(expectedAnswer)
        	.verifyComplete();
    }
    
    @Test
    void testConfirmEmailEmptyToken() {
        String confirmToken = "";
        String expectedAnswer = failedConfirmEmail();
        
        Mono<String> result = authenticationService.confirmEmail(confirmToken);
        
        StepVerifier.create(result)
        	.expectNext(expectedAnswer)
        	.verifyComplete();

    }

//    @Test
//    void testLogin() {
//        AuthenticationRequest request = new AuthenticationRequest("test@example.com", "password");
//        User user = mock(User.class);
//        when(user.isEnabled()).thenReturn(true);
//        when(jwtService.generateToken(user)).thenReturn("jwtToken");
//        when(jwtService.generateRefreshToken(user)).thenReturn("refreshToken");
//        when(authenticationManager.authenticate(any())).thenReturn(Mono.empty());
//        when(userRepository.findByEmail(request.getEmail())).thenReturn(Mono.just(user));
//
//        AuthenticationResponse result = authenticationService.login(request).block();
//        assertEquals("jwtToken", result.getAccessToken());
//        assertEquals("refreshToken", result.getRefreshToken());
//    }
    
//    @Test
//    void testLoginUserIsNotEnable() {
//        AuthenticationRequest request = new AuthenticationRequest("test@example.com", "password");
//        User user = mock(User.class);
//
//        when(authenticationManager.authenticate(any())).thenReturn(Mono.empty());
//        when(userRepository.findByEmail(request.getEmail())).thenReturn(Mono.just(user));
//        when(user.isEnabled()).thenReturn(false);
//
//        Mono<AuthenticationResponse> result = authenticationService.login(request);
//
//        StepVerifier.create(result)
//        .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException
//                && ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.FORBIDDEN
//                && ((ResponseStatusException) throwable).getReason().equals("Your account has not been confirmed. Please check your email to confirm your registration."))
//        .verify();
//    }
    
    @Test
    void testLoginUserUsernameAndPasswordFalse() {
        AuthenticationRequest request = new AuthenticationRequest("test@example.com", "password");
        
        when(authenticationManager.authenticate(any())).thenReturn(Mono.empty());
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password")));

        Mono<AuthenticationResponse> result = authenticationService.login(request);
        
        StepVerifier.create(result)
        .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException
                && ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.UNAUTHORIZED
                && ((ResponseStatusException) throwable).getReason().equals("Invalid username or password"))
        .verify();
    }
    
    @Test
    public void testRefreshToken() {   
        String userEmail = "test@example.com";
        String refreshToken = "refresh-token";
        User user = new User();
        user.setEmail(userEmail);

        ServerHttpRequest request = MockServerHttpRequest.get("/")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken)
                .build();
        MockServerHttpResponse response = new MockServerHttpResponse();
        
       
        when(jwtService.extractUsername(refreshToken)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Mono.just(user));
        when(jwtService.isTokenValid(refreshToken, user)).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("new-access-token");

        Mono<AuthenticationResponse> result = authenticationService.refreshToken(request, response);
        
        StepVerifier.create(result)
                .expectNextMatches(authResponse -> authResponse.getAccessToken().equals("new-access-token") && authResponse.getRefreshToken().equals(refreshToken))
                .verifyComplete();
    }
    
    @Test
    public void testRefreshTokenHeaderNull() {   
        ServerHttpRequest request = MockServerHttpRequest.get("/").build();
        MockServerHttpResponse response = new MockServerHttpResponse();
        
        Mono<AuthenticationResponse> result = authenticationService.refreshToken(request, response);
        
        StepVerifier.create(result)
        .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException
                && ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.BAD_REQUEST)
        .verify();
    }
    
    @Test
    public void testRefreshTokenHeaderError() {   
        String userEmail = "test@example.com";
        String refreshToken = "refresh-token";
        User user = new User();
        user.setEmail(userEmail);

        ServerHttpRequest request = MockServerHttpRequest.get("/")
                .header(HttpHeaders.AUTHORIZATION, refreshToken)
                .build();
        MockServerHttpResponse response = new MockServerHttpResponse();
        
        Mono<AuthenticationResponse> result = authenticationService.refreshToken(request, response);
        
        StepVerifier.create(result)
        .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException
                && ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.BAD_REQUEST)
        .verify();
    }
    
    @Test
    public void testRefreshTokenUsernameIsNotFound() {   
        String userEmail = null;
        String refreshToken = "refresh-token";
        User user = new User();
        user.setEmail(userEmail);

        ServerHttpRequest request = MockServerHttpRequest.get("/")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken)
                .build();
        MockServerHttpResponse response = new MockServerHttpResponse();
       
        when(jwtService.extractUsername(refreshToken)).thenReturn(userEmail);
        
        Mono<AuthenticationResponse> result = authenticationService.refreshToken(request, response);
        
        StepVerifier.create(result)
	        .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException
	                && ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.UNAUTHORIZED)
	        .verify();
    }
    
    @Test
    public void testRefreshTokenIsInvalid() {   
        String userEmail = "email@excample.com";
        String refreshToken = "invalid-refresh-token";
        User user = User.builder().email(userEmail).build();

        ServerHttpRequest request = MockServerHttpRequest.get("/")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken)
                .build();
        MockServerHttpResponse response = new MockServerHttpResponse();
       
        when(jwtService.extractUsername(refreshToken)).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Mono.just(user));
        when(jwtService.isTokenValid(refreshToken, user)).thenReturn(false);
        
        Mono<AuthenticationResponse> result = authenticationService.refreshToken(request, response);
        
        StepVerifier.create(result)
	        .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException
	                && ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.UNAUTHORIZED)
	        .verify();
    }
    
    private String failedConfirmEmail() {
		return "<html lang=\"en\">\r\n"
				+ "<head>\r\n"
				+ "	<meta charset=\"utf-8\" />\r\n"
				+ "	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\" />\r\n"
				+ "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
				+ "	<title></title>\r\n"
				+ "	<link href='https://fonts.googleapis.com/css?family=Lato:300,400|Montserrat:700' rel='stylesheet' type='text/css'>\r\n"
				+ "	<style>\r\n"
				+ "		@import url(//cdnjs.cloudflare.com/ajax/libs/normalize/3.0.1/normalize.min.css);\r\n"
				+ "		@import url(//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css);\r\n"
				+ "	</style>\r\n"
				+ "	<link rel=\"stylesheet\" href=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/default_thank_you.css\">\r\n"
				+ "	<script src=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/jquery-1.9.1.min.js\"></script>\r\n"
				+ "	<script src=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/html5shiv.js\"></script>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "	<header class=\"site-header\" id=\"header\">\r\n"
				+ "		<h1 class=\"site-header__title\" data-lead-id=\"site-header-title\">Sorry...</h1>\r\n"
				+ "	</header>\r\n"
				+ "\r\n"
				+ "	<div class=\"main-content\">\r\n"
				+ "		<i class=\"fa fa-times main-content__checkmark\" id=\"checkmark\"></i>\r\n"
				+ "		<p class=\"main-content__body\" data-lead-id=\"main-content-body\">We're sorry, but we couldn't confirm your registration on ArtConnect platform because the confirmation link has expired or is not valid anymore. Please make sure to use the latest link that we sent you in the confirmation email.</p>\r\n"
				+ "	</div>\r\n"
				+ "\r\n"
				+ "	<footer class=\"site-footer\" id=\"footer\">\r\n"
				+ "		<p class=\"site-footer__fineprint\" id=\"fineprint\">Copyright ©2014 | All Rights Reserved</p>\r\n"
				+ "	</footer>\r\n"
				+ "</body>\r\n"
				+ "</html>";
    }

}
