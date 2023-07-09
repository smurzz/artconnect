package com.artconnect.backend.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterChainProxy;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.artconnect.backend.AcBackendApplication;
import com.artconnect.backend.config.jwt.JwtAuthenticationFilter;
import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.model.user.Role;
import com.artconnect.backend.model.user.User;
import com.artconnect.backend.repository.UserRepository;

import reactor.core.publisher.Mono;

//NEED TO BE CORRECTED

@ExtendWith(MockitoExtension.class)
public class SecurityConfigTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@Mock
	private JwtService jwtService;

	@Mock
	private ReactiveAuthenticationManager reactiveAuthenticationManager;
	
	@Mock
	private ReactiveUserDetailsService reactiveUserDetailsService;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private SecurityConfig securityConfig;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

//	@Test
//	public void springWebFilterChainConfigured() {
//		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, reactiveUserDetailsService);
//		SecurityConfig securityConfig = new SecurityConfig(jwtAuthenticationFilter, reactiveAuthenticationManager);
//
//		ServerHttpSecurity http = ServerHttpSecurity.http();
//		SecurityWebFilterChain filterChain = securityConfig.springWebFilterChain(http);
//
//		User user = User.builder().email("testuser@example.com").password("password").role(Role.USER).build();
//		UserDetails userDetails = org.springframework.security.core.userdetails.User.builder().username(user.getEmail())
//				.password("password").authorities(user.getAuthorities()).build();
//		String accessToken = "accessToken";
//
//		when(jwtService.isTokenValid(accessToken, userDetails)).thenReturn(true);
//		when(jwtService.extractUsername(accessToken)).thenReturn(user.getEmail());
//		when(reactiveUserDetailsService.findByUsername("testuser@example.com")).thenReturn(Mono.just(userDetails));
//
//		webTestClient = WebTestClient
//				.bindToController(new AcBackendApplication())
//				.webFilter(new WebFilterChainProxy(filterChain))
//				.build();
//
//		assertNotNull(filterChain);
//
//		webTestClient.get()
//			.uri("/")
//			.header("Authorization", "Bearer " + accessToken)
//			.accept(MediaType.TEXT_PLAIN)
//			.exchange()
//			.expectStatus().isOk()
//			.expectBody(String.class)
//			.isEqualTo("Hello from secured ArtConnect!");
//	}
	
	@Test
	public void springWebFilterChainConfiguredUnautherized() {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, reactiveUserDetailsService);
		SecurityConfig securityConfig = new SecurityConfig(jwtAuthenticationFilter, reactiveAuthenticationManager);

		ServerHttpSecurity http = ServerHttpSecurity.http();
		SecurityWebFilterChain filterChain = securityConfig.springWebFilterChain(http);
		
		String failedToken = "failedToken";
		
		webTestClient = WebTestClient
				.bindToController(new AcBackendApplication())
				.webFilter(new WebFilterChainProxy(filterChain))
				.build();

		assertNotNull(filterChain);

		webTestClient.get()
			.uri("/")
			.header("Authorization", "Bearer " + failedToken)
			.accept(MediaType.TEXT_PLAIN)
			.exchange()
			.expectStatus().isUnauthorized();
	}

	
//	@Test
//	void testCorsConfiguration() {
//		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, reactiveUserDetailsService);
//		SecurityConfig securityConfig = new SecurityConfig(jwtAuthenticationFilter, reactiveAuthenticationManager);
//
//		ServerHttpSecurity http = ServerHttpSecurity.http();
//		SecurityWebFilterChain filterChain = securityConfig.springWebFilterChain(http);
//
//		User user = User.builder().email("testuser@example.com").password("password").role(Role.USER).build();
//		UserDetails userDetails = org.springframework.security.core.userdetails.User.builder().username(user.getEmail())
//				.password("password").authorities(user.getAuthorities()).build();
//		String accessToken = "accessToken";
//
//		when(jwtService.isTokenValid(accessToken, userDetails)).thenReturn(true);
//		when(jwtService.extractUsername(accessToken)).thenReturn(user.getEmail());
//		when(reactiveUserDetailsService.findByUsername("testuser@example.com")).thenReturn(Mono.just(userDetails));
//
//		webTestClient = WebTestClient
//				.bindToController(new AcBackendApplication())
//				.webFilter(new WebFilterChainProxy(filterChain))
//				.build();
//
//		webTestClient.get()
//			.uri("http://localhost:8080/")
//			.header("Origin", "http://localhost:3000")
//			.header("Authorization", "Bearer " + accessToken)
//			.exchange()
//			.expectStatus().isOk()
//			.expectHeader()
//			.valueEquals("Access-Control-Allow-Origin", "http://localhost:3000");
//	}
}
