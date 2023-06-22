package com.artconnect.backend.controller;

import com.artconnect.backend.controller.UserController;
import com.artconnect.backend.controller.request.UserRequest;
import com.artconnect.backend.controller.response.UserResponse;
import com.artconnect.backend.model.Image;
import com.artconnect.backend.model.user.Role;
import com.artconnect.backend.model.user.User;
import com.artconnect.backend.service.UserService;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.artconnect.backend.controller.response.UserResponse;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.MockMultipartFile;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.web.multipart.MultipartFile;



class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_shouldReturnAllUsers() {
        User user1 = new User();
        user1.setId("1");
        user1.setFirstname("John");
        user1.setLastname("Doe");
        user1.setEmail("john@example.com");

        User user2 = new User();
        user2.setId("2");
        user2.setFirstname("Jane");
        user2.setLastname("Smith");
        user2.setEmail("jane@example.com");

        when(userService.findAll()).thenReturn(Flux.just(user1, user2));

        Flux<UserResponse> response = userController.getAllUsers();

        assertEquals(2, response.count().block());
    }

    @Test
    void getUsersForAdmin_shouldReturnAllUsers() {
        User user1 = new User();
        user1.setId("1");
        user1.setFirstname("John");
        user1.setLastname("Doe");
        user1.setEmail("john@example.com");

        User user2 = new User();
        user2.setId("2");
        user2.setFirstname("Jane");
        user2.setLastname("Smith");
        user2.setEmail("jane@example.com");

        when(userService.findAll()).thenReturn(Flux.just(user1, user2));

        Flux<User> response = userController.getUsersForAdmin();

        assertNotNull(response);
        assertEquals(2, response.count().block());
    }

    @Test
    void getUserById_shouldReturnUser() {
        String userId = "1";
        User user = new User();
        user.setId(userId);
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("john@example.com");

        when(userService.findById(userId)).thenReturn(Mono.just(user));

        Mono<UserResponse> response = userController.getUserById(userId);

        assertNotNull(response);
        assertEquals(userId, response.block().getId());
        assertEquals("John", response.block().getFirstname());
        assertEquals("Doe", response.block().getLastname());
       // assertEquals("john@example.com", response.block().getEmail());
    }

    @Test
    void getUserByIdForAdmin_shouldReturnUser() {
        String userId = "1";
        User user = new User();
        user.setId(userId);
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail("john@example.com");

        when(userService.findById(userId)).thenReturn(Mono.just(user));

        Mono<User> response = userController.getUserByIdForAdmin(userId);

        assertNotNull(response);
        assertEquals(userId, response.block().getId());
        assertEquals("John", response.block().getFirstname());
        assertEquals("Doe", response.block().getLastname());
        assertEquals("john@example.com", response.block().getEmail());
    }

    @Test
    void getUserByEmail_shouldReturnUser() {
        String email = "john@example.com";
        User user = new User();
        user.setId("1");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setEmail(email);

        when(userService.findByEmail(email)).thenReturn(Mono.just(user));

        Mono<UserResponse> response = userController.getUserByEmail(email);

        assertNotNull(response);
        assertEquals("John", response.block().getFirstname());
        assertEquals("Doe", response.block().getLastname());
        //assertEquals(email, response.block().getEmail());
    }

    @Test
    void createUser_shouldCreateUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setId("1");
        userRequest.setFirstname("John");
        userRequest.setLastname("Doe");
        userRequest.setEmail("john@example.com");
        userRequest.setPassword("password");
        userRequest.setRole(Role.USER);

        Image profilePhoto = new Image();
        profilePhoto.setTitle("profile.jpg");
        userRequest.setProfilePhoto(profilePhoto);

        User user = new User();
        user.setId(userRequest.getId());
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(userRequest.getRole());
        user.setProfilePhoto(userRequest.getProfilePhoto());


        when(userService.create(any(User.class))).thenReturn(Mono.just(user));

        Mono<User> response = userController.createUser(userRequest);

        assertNotNull(response);
        assertEquals(userRequest.getId(), response.block().getId());
        assertEquals(userRequest.getFirstname(), response.block().getFirstname());
        assertEquals(userRequest.getLastname(), response.block().getLastname());
        assertEquals(userRequest.getEmail(), response.block().getEmail());
        assertEquals(userRequest.getPassword(), response.block().getPassword());
        assertEquals(userRequest.getRole(), response.block().getRole());
        assertEquals(userRequest.getProfilePhoto(), response.block().getProfilePhoto());
    }

    @Test
    void updateMyAccount_shouldUpdateUser() {
        String userId = "1";
        String authorization = "Bearer token";

        UserRequest userRequest = new UserRequest();
        userRequest.setFirstname("John");
        userRequest.setLastname("Doe");

        User user = new User();
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());

        when(userService.update(userId, user, authorization)).thenReturn(Mono.just(user));

        Mono<UserResponse> response = userController.updateMyAccount(userId, userRequest, authorization);

        assertNotNull(response);
        assertEquals(userRequest.getFirstname(), response.block().getFirstname());
        assertEquals(userRequest.getLastname(), response.block().getLastname());
    }

    @Test
    void getUserByIdForAdmin_shouldReturnUserForAdmin() {
        String userId = "1";
        User user = new User();
        user.setId(userId);
        user.setFirstname("John");
        user.setLastname("Doe");

        when(userService.findById(userId)).thenReturn(Mono.just(user));

        Mono<User> response = userController.getUserByIdForAdmin(userId);

        StepVerifier.create(response)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void deleteAllUsers_shouldDeleteAllUsers() {
        when(userService.deleteAll()).thenReturn(Mono.empty());

        Mono<Void> response = userController.deleteAllUsers();

        StepVerifier.create(response)
                .verifyComplete();
    }
    @Test
    void getUsersForAdmin_shouldReturnAllUsersForAdmin() {
        List<User> userList = Arrays.asList(
                createUser("1", "John", "Doe"),
                createUser("2", "Jane", "Smith")
        );

        when(userService.findAll()).thenReturn(Flux.fromIterable(userList));

        Flux<User> response = userController.getUsersForAdmin();

        StepVerifier.create(response)
                .expectNext(userList.get(0))
                .expectNext(userList.get(1))
                .verifyComplete();
    }



    @Test
    void deleteUser_shouldDeleteUserAndReturnNoContent() {
        String userId = "1";
        String authorization = "Bearer token";

        when(userService.delete(userId, authorization)).thenReturn(Mono.empty());

        Mono<Void> response = userController.deleteUser(userId, authorization);

        StepVerifier.create(response)
                .verifyComplete();

        verify(userService, times(1)).delete(userId, authorization);
    }

    @Test
    void getUserProfilePhotoById_shouldReturnUserProfilePhoto() {
        String userId = "1";
        Image userProfilePhoto = new Image();
        userProfilePhoto.setContentType("image/jpeg");
        userProfilePhoto.setTitle("profile_photo.jpg");
        userProfilePhoto.setImage(new Binary("image_data".getBytes())); // Provide a valid byte array for the image data

        when(userService.getProfilePhotoById(userId)).thenReturn(Mono.just(userProfilePhoto));

        Mono<ResponseEntity<byte[]>> response = userController.getUserProfilePhotoById(userId);

        StepVerifier.create(response)
                .expectNextMatches(entity -> {
                    HttpHeaders headers = entity.getHeaders();
                    byte[] body = entity.getBody();

                    assertNotNull(headers);
                    assertNotNull(body);
                    assertEquals(userProfilePhoto.getContentType(), headers.getContentType().toString());
                    assertEquals("attachment; filename=" + userProfilePhoto.getTitle(), headers.getFirst("Content-Disposition"));
                    assertArrayEquals(userProfilePhoto.getImage().getData(), body);

                    return true;
                })
                .verifyComplete();

        verify(userService, times(1)).getProfilePhotoById(userId);
    }

    private User createUser(String id, String firstname, String lastname) {
        User user = new User();
        user.setId(id);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        // Set other properties if needed
        return user;
    }
}
