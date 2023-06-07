package com.artconnect.backend.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.artconnect.backend.model.Role;
import com.artconnect.backend.repository.UserRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ApplicationConfigTests {

    @Mock
    private UserRepository userRepository;

    private ApplicationConfig applicationConfig;

    @Mock
    private ReactiveUserDetailsService userDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        applicationConfig = new ApplicationConfig(userRepository);
    }

    @Test
    public void testUserDetailsService() {
       // Mocking the UserRepository response
        com.artconnect.backend.model.User userEntity = com.artconnect.backend.model.User.builder().email("test@example.com").password("password").role(Role.USER).build();
        when(userRepository.findByEmail("test@example.com")).thenReturn(Mono.just(userEntity));

        ReactiveUserDetailsService userDetailsService = applicationConfig.userDetailsService();
        Mono<UserDetails> userDetails = userDetailsService.findByUsername("test@example.com");

        StepVerifier.create(userDetails)
                .assertNext(userDetailsResult -> {
                    // Asserting the user details
                    assertEquals("test@example.com", userDetailsResult.getUsername());
                    assertEquals("password", userDetailsResult.getPassword());
                    assertEquals("ROLE_USER", userDetailsResult.getAuthorities().iterator().next().getAuthority());
                })
                .verifyComplete();

    }

    @Test
    public void testUserDetailsServiceInvalidUsername() {
        // Mocking the UserRepository response for an invalid username
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Mono.empty());

        ReactiveUserDetailsService userDetailsService = applicationConfig.userDetailsService();
        Mono<UserDetails> userDetails = userDetailsService.findByUsername("nonexistent@example.com");

        StepVerifier.create(userDetails)
                .equals(null);
    }

    @Test
    public void reactiveAuthenticationManagerReturnsAuthenticationManager() {
        // Set up the behavior of the mock objects
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        com.artconnect.backend.model.User userEntity = com.artconnect.backend.model.User.builder()
                .email("test@example.com")
                .password(encoder.encode("password"))
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Mono.just(userEntity));

        UserDetails userDetails = new User(userEntity.getEmail(), userEntity.getPassword(), true, true, true, true, userEntity.getAuthorities());
        when(userDetailsService.findByUsername("test@example.com"))
                .thenReturn(Mono.just(userDetails));

        // Create the applicationConfig instance with the existing mocks
        applicationConfig = new ApplicationConfig(userRepository);
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(new BCryptPasswordEncoder());

        // Call the method under test
        ReactiveAuthenticationManager actualAuthenticationManager = applicationConfig.reactiveAuthenticationManager();

        Mono<Authentication> expectedAuthentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("test@example.com", "password"));
        StepVerifier.create(expectedAuthentication)
                .expectNextMatches(auth -> auth.isAuthenticated() && auth.getName().equals("test@example.com"))
                .verifyComplete();

        Mono<Authentication> actualAuthentication = actualAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken("test@example.com", "password"));
        StepVerifier.create(actualAuthentication)
                .expectNextMatches(auth -> auth.isAuthenticated() && auth.getName().equals("test@example.com"))
                .verifyComplete();

        // Verify the behavior and assertions
        assertNotNull(actualAuthenticationManager);
        assertThat(actualAuthenticationManager).isInstanceOf(UserDetailsRepositoryReactiveAuthenticationManager.class);
    }

    @Test
    public void reactiveAuthenticationManagerReturnsAuthenticationManagerInvalidPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        com.artconnect.backend.model.User userEntity = com.artconnect.backend.model.User.builder()
                .email("test@example.com")
                .password(encoder.encode("password"))
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Mono.just(userEntity));

        UserDetails userDetails = new User(userEntity.getEmail(), userEntity.getPassword(), true, true, true, true, userEntity.getAuthorities());
        when(userDetailsService.findByUsername("test@example.com"))
                .thenReturn(Mono.just(userDetails));

        // Call the method under test
        ReactiveAuthenticationManager actualAuthenticationManager = applicationConfig.reactiveAuthenticationManager();

        // Test negative case: Invalid credentials
        Mono<Authentication> invalidAuthentication = actualAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken("test@example.com", "wrongpassword"));
        StepVerifier.create(invalidAuthentication)
                .expectErrorMatches(throwable -> throwable instanceof BadCredentialsException)
                .verify();

        // Verify the behavior and assertions
        assertNotNull(actualAuthenticationManager);
        assertThat(actualAuthenticationManager).isInstanceOf(UserDetailsRepositoryReactiveAuthenticationManager.class);

    }

    @Test
    public void passwordEncoderBeanCreation() {
        ApplicationConfig applicationConfig = new ApplicationConfig(userRepository);
        PasswordEncoder passwordEncoder = applicationConfig.passwordEncoder();

        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }

}
