package com.artconnect.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.artconnect.backend.model.User;

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
        boolean isEnabled = true;
        User user1 = User.builder().isEnabled(isEnabled).email("user1@example.com").build();
        User user2 = User.builder().isEnabled(isEnabled).email("user2@example.com").build();

        when(userRepository.findByisEnabled(isEnabled)).thenReturn(Flux.just(user1, user2));

        // When
        Flux<User> foundUsersFlux = userRepository.findByisEnabled(isEnabled);

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
        boolean isEnabled = true;

        when(userRepository.findByisEnabled(isEnabled)).thenReturn(Flux.empty());

        // When
        Flux<User> foundUsersFlux = userRepository.findByisEnabled(isEnabled);

        // Then
        StepVerifier.create(foundUsersFlux)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    void testFindByIsEnabled_EmptyResult() {
        // Given
        boolean isEnabled = false;

        when(userRepository.findByisEnabled(isEnabled)).thenReturn(Flux.empty());

        // When
        Flux<User> foundUsersFlux = userRepository.findByisEnabled(isEnabled);

        // Then
        StepVerifier.create(foundUsersFlux)
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }
}