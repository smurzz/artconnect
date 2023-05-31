package com.artconnect.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    
    @Test @Disabled
    public void testRefreshTokenSuccessErrorUnauthorized() {
    	User user = User.builder().firstname("MAx").lastname("Mustermann").email("user@example.com").password("123").role(Role.USER).build();
        String refreshToken = "<refresh-invalid-token>";

        // Set up the JWT service behavior
        when(jwtService.extractUsername(refreshToken)).thenReturn(user.getEmail());
        when(jwtService.isTokenValid(refreshToken, user)).thenReturn(false);
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
}