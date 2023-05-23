package com.artconnect.backend.repository;


import com.artconnect.backend.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        // Delete all documents from the collection
        userRepository.deleteAll().block();
    }

    @AfterEach
    public void tearDown() {
        // Delete all documents from the collection
        userRepository.deleteAll().block();
    }

    @Test
    public void testFindByEmail() {
        // Given
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        userRepository.save(user).block();

        // When
        Mono<User> foundUserMono = userRepository.findByEmail(email);

        // Then
        StepVerifier.create(foundUserMono)
                .assertNext(foundUser -> assertThat(foundUser.getEmail()).isEqualTo(email))
                .expectComplete()
                .verify();
    }

    @Test
    public void testFindByIsEnabled() {
        // Given
        User user1 = new User();
        user1.setEnabled(true);
        user1.setEmail("user1@example.com"); // Set a valid email
        userRepository.save(user1).block();

        User user2 = new User();
        user2.setEnabled(false);
        user2.setEmail("user2@example.com"); // Set a valid email
        userRepository.save(user2).block();

        // When
        Flux<User> foundUsersFlux = userRepository.findByisEnabled(true);

        // Then
        StepVerifier.create(foundUsersFlux)
                .expectNextMatches(foundUser -> foundUser.isEnabled())
                .expectComplete()
                .verify();
    }
}