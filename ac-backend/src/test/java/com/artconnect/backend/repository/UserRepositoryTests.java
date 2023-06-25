package com.artconnect.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.artconnect.backend.model.user.Status;
import com.artconnect.backend.model.user.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class UserRepositoryTests {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByEmailSuccess() {
        // Given
        String email = "test@example.com";
        User user = User.builder().email(email).build();
        when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));

        // When
        Mono<User> foundUserMono = userRepository.findByEmail(email);

        // Then
        StepVerifier.create(foundUserMono)
                .assertNext(foundUser -> assertThat(foundUser.getEmail()).isEqualTo(email))
                .expectComplete()
                .verify();
    }

    @Test
    void testFindByEmailError() {
        // Given
        String email = "nouser@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Mono.empty());

        // When
        Mono<User> foundUserMono = userRepository.findByEmail(email);

        // Then
        StepVerifier.create(foundUserMono)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }
    @Test
    void testFindByIsEnabled_Success() {
        // Given
        Status isEnabled = Status.PUBLIC;
        User user1 = User.builder().isAccountEnabled(isEnabled).email("user1@example.com").build();
        User user2 = User.builder().isAccountEnabled(isEnabled).email("user2@example.com").build();

        when(userRepository.findByisAccountEnabled(isEnabled)).thenReturn(Flux.just(user1, user2));

        // When
        Flux<User> foundUsersFlux = userRepository.findByisAccountEnabled(isEnabled);

        // Then
        StepVerifier.create(foundUsersFlux)
                .expectNext(user1)
                .expectNext(user2)
                .expectComplete()
                .verify();
    }

    @Test
    void testFindByIsEnabled_NoUsersFound() {
        // Given
    	Status isEnabled = Status.PUBLIC;

        when(userRepository.findByisAccountEnabled(isEnabled)).thenReturn(Flux.empty());

        // When
        Flux<User> foundUsersFlux = userRepository.findByisAccountEnabled(isEnabled);

        // Then
        StepVerifier.create(foundUsersFlux)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void testFindByIsEnabled_EmptyResult() {
        // Given
    	Status isEnabled = Status.RESTRICTED;;

        when(userRepository.findByisAccountEnabled(isEnabled)).thenReturn(Flux.empty());

        // When
        Flux<User> foundUsersFlux = userRepository.findByisAccountEnabled(isEnabled);

        // Then
        StepVerifier.create(foundUsersFlux)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }
    
    @Test
    void testFindByFirstnameSuccessOne() {
        // Given
        String firstname = "Anna";
        User user = User.builder().firstname(firstname).build();
        when(userRepository.findByFirstnameIgnoreCase(firstname)).thenReturn(Flux.just(user));

        // When
        Flux<User> foundUserFlux = userRepository.findByFirstnameIgnoreCase(firstname);

        // Then
        StepVerifier.create(foundUserFlux)
        		.expectNext(user)
                .expectComplete()
                .verify();
    }
    
    @Test
    void testFindByLastnameSuccessOne() {
    	// Given
    	String lastname = "Black";
    	User user = User.builder().lastname(lastname).build();
    	when(userRepository.findByLastnameIgnoreCase(lastname)).thenReturn(Flux.just(user));
    	
    	// When
    	Flux<User> foundUserFlux = userRepository.findByLastnameIgnoreCase(lastname);

    	// Then
    	StepVerifier.create(foundUserFlux)
    	        .expectNext(user)
    	        .expectComplete()
    	        .verify();
    }
    
    @Test
    void testFindByFirstnameAndLastnameSuccessOne() {
    	// Given
    	String firstname = "Anna";
    	String lastname = "Black";
    	User user = User.builder().firstname(firstname).lastname(lastname).build();
    	when(userRepository.findByFirstnameIgnoreCaseAndLastnameIgnoreCase(firstname, lastname)).thenReturn(Flux.just(user));
    	
    	// When
    	Flux<User> foundUserFlux = userRepository.findByFirstnameIgnoreCaseAndLastnameIgnoreCase(firstname, lastname);

    	// Then
    	StepVerifier.create(foundUserFlux)
    	        .expectNext(user)
    	        .expectComplete()
    	        .verify();
    }
    
    @Test
    void testFindByFirstnameSuccessMultiple() {
        // Given
        String firstname = "Anna";
        User user1 = User.builder().firstname(firstname).build();
        User user2 = User.builder().firstname(firstname).build();
        when(userRepository.findByFirstnameIgnoreCase(firstname)).thenReturn(Flux.just(user1, user2));

        // When
        Flux<User> foundUserFlux = userRepository.findByFirstnameIgnoreCase(firstname);

        // Then
        StepVerifier.create(foundUserFlux)
                .expectNext(user1, user2)
                .expectComplete()
                .verify();
    }

    @Test
    void testFindByLastnameSuccessMultiple() {
        // Given
        String lastname = "Black";
        User user1 = User.builder().lastname(lastname).build();
        User user2 = User.builder().lastname(lastname).build();
        when(userRepository.findByLastnameIgnoreCase(lastname)).thenReturn(Flux.just(user1, user2));

        // When
        Flux<User> foundUserFlux = userRepository.findByLastnameIgnoreCase(lastname);

        // Then
        StepVerifier.create(foundUserFlux)
                .expectNext(user1, user2)
                .expectComplete()
                .verify();
    }

    @Test
    void testFindByFirstnameAndLastnameSuccessMultiple() {
        // Given
        String firstname = "Anna";
        String lastname = "Black";
        User user1 = User.builder().firstname(firstname).lastname(lastname).build();
        User user2 = User.builder().firstname(firstname).lastname(lastname).build();
        when(userRepository.findByFirstnameIgnoreCaseAndLastnameIgnoreCase(firstname, lastname))
                .thenReturn(Flux.just(user1, user2));

        // When
        Flux<User> foundUserFlux = userRepository.findByFirstnameIgnoreCaseAndLastnameIgnoreCase(firstname, lastname);

        // Then
        StepVerifier.create(foundUserFlux)
                .expectNext(user1, user2)
                .expectComplete()
                .verify();
    }
}