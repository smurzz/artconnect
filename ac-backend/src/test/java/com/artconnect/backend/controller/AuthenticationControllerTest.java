package com.artconnect.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.config.ApplicationConfig;
import com.artconnect.backend.config.SecurityConfig;
import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.controller.request.AuthenticationRequest;
import com.artconnect.backend.controller.request.RegisterRequest;
import com.artconnect.backend.controller.response.AuthenticationResponse;
import com.artconnect.backend.repository.UserRepository;
import com.artconnect.backend.service.AuthenticationService;

import reactor.core.publisher.Mono;


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
    
    @InjectMocks
	private AuthenticationController authenticationController;
    
    @BeforeEach
    void setup() {
    	MockitoAnnotations.openMocks(this);
    	authenticationController = new AuthenticationController(authenticationService);
    }

	@Test
    @WithMockUser
    public void testGetHelloWorld() {
	 	webTestClient = WebTestClient.bindToController(authenticationController).build();
    	
    	webTestClient.get()
	        .uri("/auth/")
	        .exchange()
	        .expectStatus().isOk()
	        .expectBody(String.class)
	        .value(response -> assertEquals("Hello from ArtConnect Security!", response));
    }

    @Test
    public void testRegisterUser() {
        RegisterRequest registerRequest = RegisterRequest
        		.builder()
        		.firstname("Max")
        		.lastname("Mustermann")
        		.email("test@test.com")
        		.password("123")
        		.build();
        
        when(authenticationService.register(registerRequest)).thenReturn(Mono.just("Verify email by the link sent on your email address"));

    	WebTestClient webTestClient = WebTestClient.bindToController(authenticationController).build();

        webTestClient.post()
	        .uri("/auth/register")
	        .contentType(MediaType.APPLICATION_JSON)
	        .body(Mono.just(registerRequest), RegisterRequest.class)
	        .exchange()
	        .expectStatus().isOk()
	        .expectBody(String.class)
	        .value(response -> assertEquals("Verify email by the link sent on your email address", response));
    }
    
    @Test
    public void testRegisterUserBadRequest() {
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
        String refreshToken = "<refresh-token>";
        String newAccessToken = "<new-access-token>";

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
                .value(response -> {
                	assertEquals("<new-access-token>", response.getAccessToken());
                	assertEquals("<refresh-token>", response.getRefreshToken());
                });
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
    	String confirmToken = "<confirm-token>";
   
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
    	when(authenticationController.confirmUserAccount("")).thenReturn(Mono.just(failedConfirmEmail()));
    	
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
    	String confirmToken = "<confirm-invalid-token>";

        when(authenticationController.confirmUserAccount(confirmToken)).thenReturn(Mono.just(failedConfirmEmail()));
        
    	WebTestClient webTestClient = WebTestClient.bindToController(authenticationController).build();

        webTestClient.get()
        	.uri("/auth/confirm-account?token={confirmToken}", confirmToken)
	        .exchange()
	        .expectStatus().isOk()
	        .expectBody(String.class)
	        .isEqualTo(failedConfirmEmail());
    }

    @Test
    public void testLoginSuccess() {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .email("test@test.com")
                .password("password")
                .build();

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
                .value(response -> {
                	assertEquals("<access-token>", response.getAccessToken());
                	assertEquals("<refresh-token>", response.getRefreshToken());
                });
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

    @Test
    public void testConfirmUserAccountMissingToken() {
        webTestClient.get()
                .uri("/auth/confirm-account")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testConfirmUserAccountExpiredToken() {
        String expiredToken = "expired-token";

        when(authenticationService.confirmEmail(expiredToken)).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)));

        webTestClient.get()
                .uri("/auth/confirm-account?token=" + expiredToken)
                .exchange()
                .expectStatus().isBadRequest();
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
}