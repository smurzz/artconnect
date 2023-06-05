package com.artconnect.backend.controller;
import com.artconnect.backend.model.User;
import com.artconnect.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.util.Date;

import static org.mockito.Mockito.*;

class UserCleanupTaskTest {
    @Test
    void cleanupUsers_RemovesExpiredUsers() {
        long confirmTokenValidity = 60000L; // 1 minute
        Date currentTime = new Date();

        // Create mock repository and sample user data
        UserRepository userRepository = mock(UserRepository.class);
        UserCleanupTask userCleanupTask = new UserCleanupTask(userRepository);

        // Create a user that should be cleaned up
        User expiredUser = new User();
        expiredUser.setEnabled(false);
        expiredUser.setCreatedAt(new Date(currentTime.getTime() - confirmTokenValidity - 1000)); // Set to expired

        // Create a user that should not be cleaned up
        User validUser = new User();
        validUser.setEnabled(false);
        validUser.setCreatedAt(new Date(currentTime.getTime() - confirmTokenValidity + 1000)); // Set to valid

        // Create a Flux of users to be returned by the repository
        Flux<User> users = Flux.just(expiredUser, validUser);
        when(userRepository.findByisEnabled(false)).thenReturn(users);

        // Create a Mono representing the deletion of the expired user
        Mono<Void> deleteResult = Mono.empty(); // Or create a successful deletion Mono if applicable
        when(userRepository.delete(expiredUser)).thenReturn(deleteResult);

        // Perform the cleanup
        userCleanupTask.cleanupUsers();

        // Verify that the delete method was called on the repository for the expired user
        verify(userRepository).delete(expiredUser);

        // Use StepVerifier to verify the reactive flow
        StepVerifier.create(deleteResult)
                .verifyComplete();
    }
}
