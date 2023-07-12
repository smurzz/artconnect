package com.artconnect.backend.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.model.Image;
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

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, imageService, jwtService, passwordEncoder);
    }

    @Test
    void testFindAll() {
        when(userRepository.findAll()).thenReturn(Flux.just(new User(), new User()));

        Flux<User> result = userService.findAll();

        StepVerifier.create(result).expectNextCount(2).verifyComplete();
    }
    
    @Test
    void testFindByFirstname() {
    	User user = User.builder().firstname("Anna").build();
    	User user2 = User.builder().firstname("Anna").build();
    	
        when(userRepository.findByFirstnameIgnoreCase(anyString())).thenReturn(Flux.just(user, user2));

        Flux<User> result = userService.findByFirstname("anna");

        StepVerifier.create(result).expectNextCount(2).verifyComplete();
    }
    
    @Test
    void testFindByLastname() {
    	User user = User.builder().lastname("Black").build();
    	User user2 = User.builder().lastname("Black").build();
    	
        when(userRepository.findByLastnameIgnoreCase(anyString())).thenReturn(Flux.just(user, user2));

        Flux<User> result = userService.findByLastname("BLACK");

        StepVerifier.create(result).expectNextCount(2).verifyComplete();
    }
    
    @Test
    void testFindByFirstnameAndOrLastname() {
    	User user = User.builder().firstname("Anna").lastname("Black").build();
    	User user2 = User.builder().firstname("Anna").lastname("Black").build();
    	
        when(userRepository.findByFirstnameIgnoreCaseAndLastnameIgnoreCase(anyString(), anyString())).thenReturn(Flux.just(user, user2));

        Flux<User> result = userService.findByFirstnameAndLastname("aNna", "BLACK");

        StepVerifier.create(result).expectNextCount(2).verifyComplete();
    }

    @Test
    void testFindById() {
        String id = "123";
        User user = User.builder().id(id).build();
        
        when(userRepository.findById(id)).thenReturn(Mono.just(user));

        Mono<User> result = userService.findById(id);

        StepVerifier.create(result)
        	.expectNext(user)
        	.verifyComplete();
    }

    @Test
    void testFindById_UserNotFound() {
        String id = "123";
        
        when(userRepository.findById(id)).thenReturn(Mono.empty());

        Mono<User> result = userService.findById(id);

        StepVerifier.create(result)
        .expectErrorSatisfies(error -> {
            Assertions.assertTrue(error instanceof ResponseStatusException);
            ResponseStatusException exception = (ResponseStatusException) error;
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
            assertEquals("User is not found.", exception.getReason());
        })
        .verify();
    }
    
    @Test
    void testFindByEmail() {
        String email = "user@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));

        Mono<User> result = userService.findByEmail(email);

        StepVerifier.create(result)
        	.expectNext(user)
        	.verifyComplete();
    }

    @Test
    void testFindByEmail_UserNotFound() {
        String email = "user@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Mono.empty());

        Mono<User> result = userService.findByEmail(email);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    Assertions.assertTrue(error instanceof ResponseStatusException);
                    ResponseStatusException exception = (ResponseStatusException) error;
                    assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
                    assertEquals("User is not found.", exception.getReason());
                })
                .verify();
    }

    @Test
    void testCreate() {
        User user = new User();
        
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        Mono<User> result = userService.create(user);

        StepVerifier.create(result)
        	.expectNext(user)
        	.verifyComplete();
    }
    
    @Test
    void testCreate_InternalServerError() {
        User user = new User();
        
        when(userRepository.save(user)).thenReturn(Mono.error(new IllegalArgumentException("Error by creating user")));

        Mono<User> result = userService.create(user);

        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                	assert error instanceof ResponseStatusException;
                    ResponseStatusException responseError = (ResponseStatusException) error;
                    assertEquals(responseError.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
                    assertEquals(responseError.getMessage(), "500 INTERNAL_SERVER_ERROR \"Error by creating user\"");
                })
                .verify();
    }

    @Test
    void testUpdate_AuthorizedUser() {
        String id = "123";
        User user = new User();
        user.setId(id);
        String authorization = "Bearer <token>";
        String userEmail = "user@example.com";

        when(jwtService.extractUsername("<token>")).thenReturn(userEmail);
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.just(user));
        when(userRepository.findById(anyString())).thenReturn(Mono.just(user)); 
        when(userRepository.save(user)).thenReturn(Mono.just(user));

        Mono<User> result = userService.update(id, user, authorization);

        StepVerifier.create(result)
            .expectNext(user)
            .verifyComplete();
    }
    
    @Test
    void testUpdate_EmptyTokenUnauthorizedUser() {
        String id = "123";
        User user = new User();
        user.setId(id);
        String authorization = null;
        
        Mono<User> result = userService.update(id, user, authorization);

        StepVerifier.create(result)
        .expectErrorSatisfies(error -> {
        	assert error instanceof ResponseStatusException;
            ResponseStatusException responseError = (ResponseStatusException) error;
            assertEquals(responseError.getStatusCode(), HttpStatus.UNAUTHORIZED);
        })
        .verify();
    }
    
    @Test
    void testUpdate_InvalidTokenUnauthorizedUser() {
        String foreign_id = "123";
        String emailString = "test@test.com";
        User user = User.builder()
        		.id("1234")
        		.email(emailString)
        		.build();
        String authorization = "Bearer <token>";
        
        when(jwtService.extractUsername("<token>")).thenReturn(emailString);
        when(userRepository.findByEmail(emailString)).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to update this account.")));
  
        Mono<User> result = userService.update(foreign_id, user, authorization);

        StepVerifier.create(result)
        .expectErrorSatisfies(error -> {
        	assert error instanceof ResponseStatusException;
            ResponseStatusException responseError = (ResponseStatusException) error;
            assertEquals(responseError.getStatusCode(), HttpStatus.UNAUTHORIZED);
            assertEquals(responseError.getMessage(), "401 UNAUTHORIZED \"You are not authorized to update this account.\"");
        })
        .verify();
    }
    
    @Test
    void testUpdate_UserNotFound() {
        String id = "123";
        User user = new User();
        user.setId(id);
        String authorization = "Bearer <token>";
        String userEmail = "user@example.com";

        when(jwtService.extractUsername("<token>")).thenReturn(userEmail);
        when(userRepository.findByEmail(userEmail)).thenReturn(Mono.empty());

        Mono<User> result = userService.update(id, user, authorization);

        StepVerifier.create(result)
        .expectErrorSatisfies(error -> {
        	assert error instanceof ResponseStatusException;
            ResponseStatusException responseError = (ResponseStatusException) error;
            assertEquals(responseError.getStatusCode(), HttpStatus.NOT_FOUND);
            assertEquals(responseError.getMessage(), "404 NOT_FOUND \"User is not found.\"");
        })
        .verify();
    }
    
    @Test
    void testAddProfilePhoto_ReturnProfilePhoto() {
    	 FilePart filePart = mock(FilePart.class);
    	    String authorization = "Bearer <token>";

    	    String email = "example@test.com";
    	    String oldImageId = "oldProfilePhotoId";
    	    User user = User.builder().email(email).build();
    	    Image oldProfilePhoto = new Image();
    	    oldProfilePhoto.setId(oldImageId);
    	    user.setProfilePhoto(oldProfilePhoto);

    	    Image newProfilePhoto = new Image();
    	    newProfilePhoto.setId("newProfilePhotoId");

    	    when(jwtService.extractUsername("<token>")).thenReturn(email);
    	    when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));
    	    when(imageService.addPhoto(any(Mono.class))).thenReturn(Mono.just(newProfilePhoto));
    	    when(userRepository.save(user)).thenReturn(Mono.just(user));
    	    when(imageService.deleteById(oldImageId)).thenReturn(Mono.empty());

    	    Mono<Image> result = userService.addProfilePhoto(Mono.just(filePart), authorization);

    	    StepVerifier.create(result)
    	            .expectNextMatches(actualImage -> actualImage.getId().equals(newProfilePhoto.getId()))
    	            .verifyComplete();

    	    verify(imageService).deleteById(oldImageId);
    }

    @Test
    void testAddProfilePhoto_InvalidAuthorizationToken() {
    	FilePart filePart = mock(FilePart.class);
    	Long imageSize = 3456545L;
        String authorization = "";

        Mono<Image> result = userService.addProfilePhoto(Mono.just(filePart), authorization);

        StepVerifier.create(result)
        .expectErrorSatisfies(error -> {
        	assert error instanceof ResponseStatusException;
            ResponseStatusException responseError = (ResponseStatusException) error;
            assertEquals(responseError.getStatusCode(), HttpStatus.UNAUTHORIZED);
        })
        .verify();
    }

    @Test
    void testAddProfilePhoto_InternalServerError() {
    	FilePart filePart = mock(FilePart.class);
    	Long imageSize = 3456545L;
        String authorization = "Bearer <token>";

        String email = "example@test.com";
        User user = User.builder().email(email).build();             
        Image image = new Image();
        user.setProfilePhoto(image);
        
        when(jwtService.extractUsername("<token>")).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));
        when(imageService.addPhoto(any(Mono.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to insert profile photo.")));
        
        Mono<Image> result = userService.addProfilePhoto(Mono.just(filePart), authorization);

        StepVerifier.create(result)
        .expectErrorSatisfies(error -> {
        	assert error instanceof ResponseStatusException;
            ResponseStatusException responseError = (ResponseStatusException) error;
            assertEquals(responseError.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
            assertEquals(responseError.getMessage(), "500 INTERNAL_SERVER_ERROR \"Failed to insert profile photo.\"");
        })
        .verify();
    }
    
    @Test
    void testAddProfilePhoto_NotFound() {
    	FilePart filePart = mock(FilePart.class);
    	Long imageSize = 3456545L;
        String authorization = "Bearer <token>";

        String email = "example@test.com";
        User user = User.builder().email(email).build();             
        Image image = new Image();
        user.setProfilePhoto(image);
        
        when(jwtService.extractUsername("<token>")).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
       
        Mono<Image> result = userService.addProfilePhoto(Mono.just(filePart), authorization);

        StepVerifier.create(result)
        .expectErrorSatisfies(error -> {
        	assert error instanceof ResponseStatusException;
            ResponseStatusException responseError = (ResponseStatusException) error;
            assertEquals(responseError.getStatusCode(), HttpStatus.UNAUTHORIZED);
        })
        .verify();
    }
    

    @Test
    void testGetProfilePhotoById_WithExistingUser_ShouldReturnProfilePhoto() {
        String userId = "123";
        User user = new User();
        Image image = new Image();
        user.setProfilePhoto(image);

        when(userRepository.findById(userId)).thenReturn(Mono.just(user));

        Mono<Image> result = userService.getProfilePhotoById(userId);

        StepVerifier.create(result)
	        .expectNext(image)
	        .verifyComplete();
    }

    @Test
    void testGetProfilePhotoById_WithNonExistingUser_ReturnNotFound() {
    	String userId = "123";
        User user = User.builder().id(userId).build();
        Image image = new Image();
        user.setProfilePhoto(image);

        when(userRepository.findById(userId)).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + userId + " is not found.")));

        Mono<Image> result = userService.getProfilePhotoById(userId);

        StepVerifier.create(result)
	        .expectErrorSatisfies(error -> {
	        	assert error instanceof ResponseStatusException;
	            ResponseStatusException responseError = (ResponseStatusException) error;
	            assertEquals(responseError.getStatusCode(), HttpStatus.NOT_FOUND);
	            assertEquals(responseError.getMessage(), "404 NOT_FOUND \"User with id " + userId + " is not found.\"");
	        })
	        .verify();
    }
    
    @Test
    void testGetProfilePhotoById_WithNoProfilePhoto_ReturnNotFound() {
    	String userId = "123";
        User user = User.builder().id(userId).build();
        Image image = new Image();
        user.setProfilePhoto(image);

        when(userRepository.findById(userId)).thenReturn(Mono.error(new NullPointerException("User with id " + userId + " has no profile photo.")));

        Mono<Image> result = userService.getProfilePhotoById(userId);

        StepVerifier.create(result)
	        .expectErrorSatisfies(error -> {
	        	assert error instanceof ResponseStatusException;
	            ResponseStatusException responseError = (ResponseStatusException) error;
	            assertEquals(responseError.getStatusCode(), HttpStatus.NOT_FOUND);
	            assertEquals(responseError.getMessage(), "404 NOT_FOUND \"User with id " + userId + " has no profile photo.\"");
	        })
	        .verify();
    }
   
    @Test
    void testDeleteById_ReturnNoContent() {
        String id = "123";
        String email = "user@example.com";
        String authorization = "Bearer <token>";
        User user = User.builder()
        		.id("123")
        		.email("user@example.com")
        		.build();

        when(jwtService.extractUsername(anyString())).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(Mono.just(user));
        when(userRepository.deleteById(id)).thenReturn(Mono.empty());

        Mono<Void> result = userService.delete(id, authorization);

        StepVerifier.create(result)
        	.verifyComplete();
        verify(userRepository).deleteById(id);
    }

    @Test
    void testDelete_UserNotFound() {
        String id = "123";
        String authorization = "Bearer <token>";

        when(jwtService.extractUsername(anyString())).thenReturn("user@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found.")));

        Mono<Void> result = userService.delete(id, authorization);

        StepVerifier.create(result)
	        .expectErrorSatisfies(error -> {
	        	assert error instanceof ResponseStatusException;
	            ResponseStatusException responseError = (ResponseStatusException) error;
	            assertEquals(responseError.getStatusCode(), HttpStatus.NOT_FOUND);
	            assertEquals(responseError.getMessage(), "404 NOT_FOUND \"User is not found.\"");
	        })
	        .verify();

        verify(userRepository).findByEmail(anyString());
        verify(userRepository, never()).deleteById(anyString());
    }
    
    @Test
    void testDelete__ReturnsUnauthorizedError() {
        String id = "123";
        String authorization = "";

        Mono<Void> result = userService.delete(id, authorization);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof ResponseStatusException &&
                                ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.UNAUTHORIZED)
                .verify();

        verifyNoInteractions(userRepository);
    }
    
    @Test
    void testDeleteInvalidToken__ReturnsUnauthorizedError() {
        String id = "123";
        String authorization = "Bearer <token>";

        when(jwtService.extractUsername(anyString())).thenReturn("user@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to delete this account.")));
        
        Mono<Void> result = userService.delete(id, authorization);

        StepVerifier.create(result)
        .expectErrorSatisfies(error -> {
        	assert error instanceof ResponseStatusException;
            ResponseStatusException responseError = (ResponseStatusException) error;
            assertEquals(responseError.getStatusCode(), HttpStatus.UNAUTHORIZED);
            assertEquals(responseError.getMessage(), "401 UNAUTHORIZED \"You are not authorized to delete this account.\"");
        })
        .verify();
    }
    
    @Test
    void deleteAll_Success() {

    	when(userRepository.deleteAll()).thenReturn(Mono.empty());

        Mono<Void> result = userService.deleteAll();

        StepVerifier.create(result)
                .verifyComplete();

        verify(userRepository).deleteAll();
    }

}