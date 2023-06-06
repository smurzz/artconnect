package com.artconnect.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.artconnect.backend.controller.request.AuthenticationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.config.ApplicationConfig;
import com.artconnect.backend.config.SecurityConfig;
import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.controller.request.RegisterRequest;
import com.artconnect.backend.controller.response.AuthenticationResponse;
import com.artconnect.backend.model.Role;
import com.artconnect.backend.model.User;
import com.artconnect.backend.repository.UserRepository;
import com.artconnect.backend.service.AuthenticationService;
import com.artconnect.backend.service.EmailService;

import io.netty.handler.codec.spdy.SpdyHttpHeaders;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

@WebFluxTest(controllers = AuthenticationController.class)
@Import({ SecurityConfig.class, ApplicationConfig.class })
public class AuthenticationControllerTest {
	
	@Autowired
	private WebTestClient webTestClient;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;
    
    @BeforeEach
    void setup() {
    	MockitoAnnotations.openMocks(this);
    }

	@Test
    @WithMockUser
    public void testGetHelloWorld() {
	 	AuthenticationController authenticationController = mock(AuthenticationController.class);

		WebTestClient webTestClient = WebTestClient.bindToController(authenticationController).build();
    	
    	when(authenticationController.getHelloWorldFromAuth())
        	.thenReturn(Mono.just("Hello from ArtConnect Security!"));

    	webTestClient.get()
	        .uri("/auth/")
	        .exchange()
	        .expectStatus().isOk()
	        .expectBody(String.class)
	        .value(response -> assertEquals("Hello from ArtConnect Security!", response));
    }

    @Test
    public void testRegisterUser() {
    	AuthenticationController authenticationController = mock(AuthenticationController.class);
    	AuthenticationService authenticationService = mock(AuthenticationService.class);
    	UserRepository userRepository = mock(UserRepository.class);
    	
        RegisterRequest registerRequest = RegisterRequest
        		.builder()
        		.firstname("Max")
        		.lastname("Mustermann")
        		.email("test@test.com")
        		.password("123")
        		.build();
        
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(User.builder().build()));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));
        when(authenticationService.register(registerRequest)).thenReturn(Mono.just("Verify email by the link sent on your email address"));

    	WebTestClient webTestClient = WebTestClient.bindToController(authenticationController).build();

        webTestClient.post()
	        .uri("/auth/register")
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(Mono.just(registerRequest), RegisterRequest.class)
	        .exchange()
	        .expectStatus().isOk()
	        .expectBody(String.class);
    }
    
    @Test
    public void testRegisterUserBadRequest() {
    	AuthenticationController authenticationController = mock(AuthenticationController.class);
    	AuthenticationService authenticationService = mock(AuthenticationService.class);
    	
        RegisterRequest registerRequest = RegisterRequest
        		.builder()
        		.firstname("Max")
        		.lastname("Mustermann")
        		.build();
        
        when(authenticationService.register(registerRequest)).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)));

    	WebTestClient webTestClient = WebTestClient.bindToController(authenticationController).build();

        webTestClient.post()
	        .uri("/auth/register")
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(Mono.just(registerRequest), RegisterRequest.class)
	        .exchange()
	        .expectStatus().isBadRequest();
    }

    @Test
    public void testRefreshTokenSuccess() {
    	User user = User.builder().firstname("MAx").lastname("Mustermann").email("user@example.com").password("123").role(Role.USER).build();
        String refreshToken = "<refresh-token>";
        String newAccessToken = "<new-access-token>";

        // Set up the JWT service behavior
        when(jwtService.extractUsername(refreshToken)).thenReturn(user.getEmail());
        when(jwtService.isTokenValid(refreshToken, user)).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn(newAccessToken);

        // Set up the user repository behavior
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Mono.just(user));

        // Set up the authentication service behavior
        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .build();
        
        when(authenticationService.refreshToken(any(ServerHttpRequest.class), any(ServerHttpResponse.class))).thenReturn(Mono.just(authResponse));
        
        // Perform the refresh token request
        webTestClient.post()
                .uri("/auth/refresh")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthenticationResponse.class)
                .isEqualTo(authResponse);
    }
    
    @Test 
    public void testRefreshTokenErrorUnauthorized() {
        String refreshToken = "<refresh-invalid-token>";

        // Set up the JWT service behavior
        when(authenticationService.refreshToken(any(ServerHttpRequest.class), any(ServerHttpResponse.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
        
        // Perform the refresh token request
        webTestClient.post()
                .uri("/auth/refresh")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken)
                .exchange()
                .expectStatus().isUnauthorized();
    }
    
    @Test
    public void testRefreshTokenMissingAuthorizationHeader() {
    	when(authenticationService.refreshToken(any(ServerHttpRequest.class), any(ServerHttpResponse.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        
        webTestClient.post()
                .uri("/auth/refresh")
                .header(HttpHeaders.AUTHORIZATION, "")
                .exchange()
                .expectStatus().isBadRequest();
    }
    
    @Test
    public void testConfirmAccount() {
    	AuthenticationController authenticationController = mock(AuthenticationController.class);
    	AuthenticationService authenticationService = mock(AuthenticationService.class);
    	ReactiveUserDetailsService userDetailsService = mock(ReactiveUserDetailsService.class);
    	
    	UserRepository userRepository = mock(UserRepository.class);
    	String confirmToken = "<confirm-token>";
    	
        User user = User
        		.builder()
        		.firstname("Max")
        		.lastname("Mustermann")
        		.email("test@test.com")
        		.password("123")
        		.isEnabled(true)
        		.build();
        UserDetails userDetails = User.builder()
        		.email("test@test.com")
        		.password("123")
        		.role(Role.USER)
        		.build();

        when(jwtService.extractUsername(confirmToken)).thenReturn("test@test.com");
        when(userDetailsService.findByUsername("test@test.com")).thenReturn(Mono.just(userDetails));
        when(jwtService.isTokenValid(confirmToken, userDetails)).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));
        when(authenticationService.confirmEmail(confirmToken)).thenReturn(Mono.just(succeedConfirmEmail()));
        when(authenticationController.confirmUserAccount(confirmToken)).thenReturn(Mono.just(succeedConfirmEmail()));

    	WebTestClient webTestClient = WebTestClient.bindToController(authenticationController).build();

        webTestClient.get()
        	.uri("/auth/confirm-account?token={confirmToken}", confirmToken)
	        .exchange()
	        .expectStatus().isOk()
	        .expectBody(String.class)
	        .isEqualTo(succeedConfirmEmail());
    }
    
    @Test
    public void testConfirmAccountEmptyParamRequest() {
    	PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    	ReactiveAuthenticationManager authenticationManager = mock(ReactiveAuthenticationManager.class);
    	ReactiveUserDetailsService userDetailsService = mock(ReactiveUserDetailsService.class);
    	EmailService emailService = mock(EmailService.class);
    	
    	AuthenticationService authenticationService = new AuthenticationService(userRepository, passwordEncoder, jwtService, authenticationManager, userDetailsService, emailService);
    	AuthenticationController authenticationController = new AuthenticationController(authenticationService);

    	WebTestClient webTestClient = WebTestClient.bindToController(authenticationController).build();

        webTestClient.get()
        	.uri("/auth/confirm-account?token=")
	        .exchange()
	        .expectStatus().isOk()
	        .expectBody(String.class)
	        .isEqualTo(failedConfirmEmail());
    }
    
    @Test
    public void testConfirmAccountExpiredToken() {
    	AuthenticationController authenticationController = mock(AuthenticationController.class);
    	AuthenticationService authenticationService = mock(AuthenticationService.class);
    	ReactiveUserDetailsService userDetailsService = mock(ReactiveUserDetailsService.class);
    	
    	String confirmToken = "<confirm-invalid-token>";

        UserDetails userDetails = User.builder()
        		.email("test@test.com")
        		.password("123")
        		.role(Role.USER)
        		.build();

        when(jwtService.extractUsername(confirmToken)).thenReturn("test@test.com");
        when(userDetailsService.findByUsername("test@test.com")).thenReturn(Mono.just(userDetails));
        when(jwtService.isTokenValid(confirmToken, userDetails)).thenReturn(false);
        when(authenticationService.confirmEmail(confirmToken)).thenReturn(Mono.just(failedConfirmEmail()));
        when(authenticationController.confirmUserAccount(confirmToken)).thenReturn(Mono.just(failedConfirmEmail()));

    	WebTestClient webTestClient = WebTestClient.bindToController(authenticationController).build();

        webTestClient.get()
        	.uri("/auth/confirm-account?token={confirmToken}", confirmToken)
	        .exchange()
	        .expectStatus().isOk()
	        .expectBody(String.class)
	        .isEqualTo(failedConfirmEmail());
    }
    
	private String succeedConfirmEmail() {
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

    @Test
    public void testLoginSuccess() {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("test@test.com")
                .password("password")
                .build();

        User user = User.builder()
                .id("123")
                .email("test@test.com")
                .password("$2a$10$samplepasswordhash")
                .isEnabled(true)
                .build();

        // Set up the user repository behavior
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Mono.just(user));

        // Set up the authentication service behavior
        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .accessToken("<access-token>")
                .refreshToken("<refresh-token>")
                .build();
        when(authenticationService.login(authenticationRequest)).thenReturn(Mono.just(authResponse));

        // Perform the login request
        webTestClient.post()
                .uri("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthenticationResponse.class)
                .isEqualTo(authResponse);
    }

    @Test
    public void testConfirmUserAccountServerError() {
        String confirmationToken = "valid-token";

        when(authenticationService.confirmEmail(confirmationToken)).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)));

        webTestClient.get()
                .uri("/auth/confirm-account?token={confirmationToken}", confirmationToken)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    public void testConfirmUserAccountInvalidToken() {
        String confirmationToken = "invalid-token";

        when(authenticationService.confirmEmail(confirmationToken)).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)));

        webTestClient.get()
                .uri("/auth/confirm-account?token={confirmationToken}", confirmationToken)
                .exchange()
                .expectStatus().isBadRequest();
    }

}