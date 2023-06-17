package com.artconnect.backend.service;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Date;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.model.Image;
import com.artconnect.backend.model.user.Role;
import com.artconnect.backend.model.user.User;
import com.artconnect.backend.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ImageService imageService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, imageService, jwtService, passwordEncoder);
    }

    @Test
    public void testFindAll() {
        when(userRepository.findAll()).thenReturn(Flux.just(new User(), new User()));

        Flux<User> result = userService.findAll();

        StepVerifier.create(result).expectNextCount(2).verifyComplete();
    }

    @Test
    public void testFindById() {
        String id = "123";
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Mono.just(user));

        Mono<User> result = userService.findById(id);

        StepVerifier.create(result).expectNext(user).verifyComplete();
    }

    @Test
    public void testFindById_UserNotFound() {
        String id = "123";
        when(userRepository.findById(id)).thenReturn(Mono.empty());

        Mono<User> result = userService.findById(id);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof ResponseStatusException &&
                                ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.BAD_REQUEST)
                .verify();
    }

    @Test
    public void testCreate() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        Mono<User> result = userService.create(user);

        StepVerifier.create(result).expectNext(user).verifyComplete();
    }

    @Test
    public void testUpdate_AuthorizedUser() {
        String id = "123";
        User user = new User();
        user.setId(id); // Set the ID property
        String authorization = "Bearer <token>";
        String userEmail = "user@example.com";

        when(jwtService.extractUsername(anyString())).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Mono.just(user));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        Mono<User> result = userService.update(id, user, authorization);

        StepVerifier.create(result).expectNext(user).verifyComplete();
    }

    @Test
    public void testFindById_UserFound() {
        String id = "123";
        User user = new User();
        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Mono.just(user));

        Mono<User> result = userService.findById(id);

        StepVerifier.create(result).expectNext(user).verifyComplete();
    }


    @Test
    public void testFindByEmail_UserFound() {
        String email = "user@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));

        Mono<User> result = userService.findByEmail(email);

        StepVerifier.create(result).expectNext(user).verifyComplete();
    }

    @Test
    public void testFindByEmail_UserNotFound() {
        String email = "user@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Mono.empty());

        Mono<User> result = userService.findByEmail(email);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    Assertions.assertTrue(error instanceof ResponseStatusException);
                    ResponseStatusException exception = (ResponseStatusException) error;
                    Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
                    Assertions.assertEquals("User is not found.", exception.getReason());
                })
                .verify();
    }

    @Test
    public void testCreate_ValidUser() {
        User user = new User();

        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        Mono<User> result = userService.create(user);

        StepVerifier.create(result).expectNext(user).verifyComplete();
    }

    @Test
    public void testDeleteById_UserNotFound() {
        String id = "123";
        String authorization = "Bearer <token>";

        when(jwtService.extractUsername(anyString())).thenReturn("user@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.empty());

        Mono<Void> result = userService.delete(id, authorization);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    Assertions.assertTrue(error instanceof ResponseStatusException);
                    ResponseStatusException exception = (ResponseStatusException) error;
                    Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
                    Assertions.assertEquals("User is not found.", exception.getReason());
                })
                .verify();
    }

    @Test
    void createUser_Success() {
        // Arrange
        User user = new User();
        user.setCreatedAt(new Date());

        when(userRepository.save(user)).thenReturn(Mono.just(user));

        // Act
        Mono<User> result = userService.create(user);

        // Assert
        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();

        verify(userRepository).save(user);
    }

    @Test
    void createUser_Error() {
        // Arrange
        User user = new User();
        user.setCreatedAt(new Date());

        when(userRepository.save(user)).thenReturn(Mono.error(new IllegalArgumentException()));

        // Act
        Mono<User> result = userService.create(user);

        // Assert
        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assert error instanceof ResponseStatusException;
                    assert ((ResponseStatusException) error).getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR;
                })
                .verify();

        verify(userRepository).save(user);
    }

    @Test
    void deleteAll_Success() {
        // Arrange
        when(userRepository.deleteAll()).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = userService.deleteAll();

        // Assert
        StepVerifier.create(result)
                .verifyComplete();

        verify(userRepository).deleteAll();
    }

    @Test
    public void testGetProfilePhotoById_WithExistingUser_ShouldReturnProfilePhoto() {
        // Arrange
        String userId = "123";
        User user = new User();
        user.setProfilePhoto(new Image());

        when(userRepository.findById(userId)).thenReturn(Mono.just(user));

        // Act
        Mono<Image> result = userService.getProfilePhotoById(userId);

        // Assert
        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetProfilePhotoById_WithNonExistingUser_ShouldThrowException() {
        // Arrange
        String userId = "456";

        when(userRepository.findById(userId)).thenReturn(Mono.empty());

        // Act
        Mono<Image> result = userService.getProfilePhotoById(userId);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof ResponseStatusException
                                && ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.NOT_FOUND
                                && throwable.getMessage().contains("User with id " + userId + " is not found.")
                )
                .verify();

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetProfilePhotoById_WithUserWithoutProfilePhoto_ShouldThrowException() {
        // Arrange
        String userId = "789";
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Mono.just(user));

        // Act
        Mono<Image> result = userService.getProfilePhotoById(userId);

        // Assert
        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof ResponseStatusException
                                && ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.NOT_FOUND
                                && throwable.getMessage().contains("User with id " + userId + " has no profile photo.")
                )
                .verify();

        verify(userRepository, times(1)).findById(userId);
    }
}