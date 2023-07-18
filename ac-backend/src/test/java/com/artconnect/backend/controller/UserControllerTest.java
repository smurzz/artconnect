package com.artconnect.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.config.ApplicationConfig;
import com.artconnect.backend.config.SecurityConfig;
import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.controller.request.UserRequest;
import com.artconnect.backend.controller.response.UserResponse;
import com.artconnect.backend.model.Image;
import com.artconnect.backend.model.user.Role;
import com.artconnect.backend.model.user.User;
import com.artconnect.backend.repository.UserRepository;
import com.artconnect.backend.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = UserController.class)
@Import({ SecurityConfig.class, ApplicationConfig.class })
class UserControllerTest {

	@MockBean
	private JwtService jwtService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private UserService userService;

	@InjectMocks
	private UserController userController;

	@Autowired
	private WebTestClient webTestClient;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		userController = new UserController(userService);
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

		webTestClient.get()
				.uri("/users")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(UserResponse.class)
				.consumeWith(response -> {
					List<UserResponse> userResponses = response.getResponseBody();
					assertThat(userResponses).isNotNull();
					assertThat(userResponses).contains(UserResponse.fromUser(user1));
					assertThat(userResponses).contains(UserResponse.fromUser(user2));
				});
	}
	
	@Test
	void getUsersByFirstnameAndOrLastname_shouldReturnUsersByFirstname() {
		User user2 = new User();
		user2.setFirstname("Jane");
		user2.setLastname("Smith");
		user2.setEmail("jane@example.com");

		when(userService.searchUsers("Jane")).thenReturn(Flux.just(user2));

		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/users/search")
						.queryParam("q", "Jane")
						.build())
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(UserResponse.class)
				.consumeWith(response -> {
					List<UserResponse> userResponses = response.getResponseBody();
					assertThat(userResponses).isNotNull();
					assertThat(userResponses).contains(UserResponse.fromUser(user2));
				});
	}
	
	@Test
	void getUsersByFirstnameAndOrLastname_shouldReturnUsersByLastname() {
		User user = new User();
		user.setFirstname("Jane");
		user.setLastname("Smith");
		user.setEmail("jane@example.com");
		
		User user2 = new User();
		user.setFirstname("Kate");
		user.setLastname("Smith");
		user.setEmail("kate@example.com");


		when(userService.searchUsers(anyString())).thenReturn(Flux.just(user, user2));

		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/users/search")
						.queryParam("q", "smith")
						.build())
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(UserResponse.class)
				.consumeWith(response -> {
					List<UserResponse> userResponses = response.getResponseBody();
					assertThat(userResponses).isNotNull();
					assertThat(userResponses).contains(UserResponse.fromUser(user));
					assertThat(userResponses).contains(UserResponse.fromUser(user2));
				});
	}
	
	@Test
	void getUsersByLastnameAndOrLastname_shouldReturnUsers() {
		User user = new User();
		user.setFirstname("Kate");
		user.setLastname("Smith");
		user.setEmail("kate2@example.com");
		
		User user2 = new User();
		user.setFirstname("Kate");
		user.setLastname("Smith");
		user.setEmail("kate@example.com");


		when(userService.findByFirstnameAndLastname(anyString(), anyString())).thenReturn(Flux.just(user, user2));

		webTestClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/users/search")
						.queryParam("firstname", "kate")
						.queryParam("lastname", "smith")
						.build())
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(UserResponse.class)
				.consumeWith(response -> {
					List<UserResponse> userResponses = response.getResponseBody();
					assertThat(userResponses).isNotNull();
					assertThat(userResponses).contains(UserResponse.fromUser(user));
					assertThat(userResponses).contains(UserResponse.fromUser(user2));
				});
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void getUsersForAdmin_shouldReturnAllUsers() {
		User user1 = new User();
		user1.setId("1");
		user1.setFirstname("John");
		user1.setLastname("Doe");
		user1.setRole(Role.USER);
		user1.setEmail("john@example.com");

		User user2 = new User();
		user2.setId("2");
		user2.setFirstname("Jane");
		user2.setLastname("Smith");
		user2.setRole(Role.USER);
		user2.setEmail("jane@example.com");

		when(userService.findAll()).thenReturn(Flux.just(user1, user2));

		webTestClient
				.get()
				.uri("/users/admin")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(User.class)
				.consumeWith(response -> {
					List<User> userResponses = response.getResponseBody();
					assertThat(userResponses).isNotNull();
					assertThat(userResponses).contains(user1);
					assertThat(userResponses).contains(user2);
				});
	}
	
	@Test
	@WithMockUser
	void getUsersForAdmin_shouldReturnForbitten() {
		webTestClient
				.get()
				.uri("/users/admin")
				.exchange()
				.expectStatus().isForbidden();
	}
	
	@Test
	void getUsersForAdmin_shouldReturnUnauthorized() {
		webTestClient
				.get()
				.uri("/users/admin")
				.exchange()
				.expectStatus().isUnauthorized();
	}
	
	
	@Test
	void getUserById_shouldReturnUser() {
		String userId = "1";
		User user = User.builder()
				.id(userId)
				.firstname("John")
				.lastname("Doe")
				.email("john@example.com")
				.build();

		when(userService.findById("1")).thenReturn(Mono.just(user));

		webTestClient
				.get()
				.uri("/users/{id}", "1")
				.exchange()
				.expectStatus().isOk()
				.expectBody(UserResponse.class)
				.isEqualTo(UserResponse.fromUser(user));
	}
	
	@Test
	void getUserById_shouldReturnNotFound() {

		when(userService.findById("1")).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found.")));

		webTestClient
				.get()
				.uri("/users/{id}", "1")
				.exchange()
				.expectStatus().isNotFound();
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void getUserByIdForAdmin_shouldReturnUser() {
		String userId = "1";
		User user = User.builder()
				.id(userId)
				.firstname("John")
				.lastname("Doe")
				.email("john@example.com")
				.password("password")
				.role(Role.USER)
				.build();

		when(userService.findById("1")).thenReturn(Mono.just(user));

		webTestClient
				.get()
				.uri("/users/{id}/admin", userId)
				.exchange()
				.expectStatus().isOk()
				.expectBody(User.class)
				.isEqualTo(user);
	}
	
	@Test
	@WithMockUser
	void getUserByIdForAdmin_shouldReturnForbitten() {
		webTestClient
				.get()
				.uri("/users/{id}/admin", "1")
				.exchange()
				.expectStatus().isForbidden();
	}
	
	@Test
	void getUserByIdForAdmin_shouldReturnUnauthorized() {
		webTestClient
				.get()
				.uri("/users/{id}/admin", "1")
				.exchange()
				.expectStatus().isUnauthorized();
	}
	
	@Test
	void getUserByEmail_shouldReturnUser() {
		String email = "john@example.com";
		User user = User.builder()
				.id("1")
				.firstname("John")
				.lastname("Doe")
				.email("john@example.com")
				.build();

		when(userService.findByEmail(email)).thenReturn(Mono.just(user));

		webTestClient
				.get()
				.uri(uriBuilder -> uriBuilder
					.path("/users")
					.queryParam("email", email)
					.build())
				.exchange()
				.expectStatus().isOk()
				.expectBody(UserResponse.class)
				.isEqualTo(UserResponse.fromUser(user));
	}
	
	@Test
	void getUserByEmail_shouldReturnNotFound() {
		String email = "john@example.com";

		when(userService.findByEmail(email)).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found.")));

		webTestClient
				.get()
				.uri(uriBuilder -> uriBuilder
					.path("/users")
					.queryParam("email", email)
					.build())
				.exchange()
				.expectStatus().isNotFound();
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void createUser_shouldCreateUser() {
		UserRequest userRequest = new UserRequest();
		userRequest.setId("1");
		userRequest.setFirstname("John");
		userRequest.setLastname("Doe");
		userRequest.setEmail("john@example.com");
		userRequest.setPassword("password");
		userRequest.setRole(Role.USER);

		User user = new User();
		user.setId(userRequest.getId());
		user.setFirstname(userRequest.getFirstname());
		user.setLastname(userRequest.getLastname());
		user.setEmail(userRequest.getEmail());
		user.setPassword(userRequest.getPassword());
		user.setRole(userRequest.getRole());
		user.setCreatedAt(new Date());
		
		when(userService.create(any(User.class))).thenReturn(Mono.just(user));

		webTestClient
			.post()
			.uri("/users")
			.contentType(MediaType.APPLICATION_JSON)
	        .body(Mono.just(userRequest), UserRequest.class)
			.exchange()
			.expectStatus().isCreated()
			.expectBody(User.class)
			.isEqualTo(user);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void createUser_shouldReturnArgumentIsNull() {
		UserRequest userRequest = new UserRequest();
				
		when(userService.create(any(User.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error by creating user")));

		webTestClient
			.post()
			.uri("/users")
			.contentType(MediaType.APPLICATION_JSON)
	        .body(Mono.just(userRequest), UserRequest.class)
			.exchange()
			.expectStatus().is5xxServerError();
	}
	
	@Test
	@WithMockUser
	void createUser_shouldReturnForbitten() {
		webTestClient
				.post()
				.uri("/users")
				.contentType(MediaType.APPLICATION_JSON)
		        .body(Mono.just(new UserRequest()), UserRequest.class)
				.exchange()
				.expectStatus().isForbidden();
	}
	
	@Test
	void createUser_shouldReturnUnauthorized() {
		webTestClient
				.post()
				.uri("/users/")
				.contentType(MediaType.APPLICATION_JSON)
		        .body(Mono.just(new UserRequest()), UserRequest.class)
				.exchange()
				.expectStatus().isUnauthorized();
	}
	
	@Test
	@WithMockUser
	void updateMyAccount_shouldUpdateUser() {
		String userId = "1";
		String authorization = "Bearer token";

		UserRequest userRequest = new UserRequest();
		userRequest.setFirstname("John");
		userRequest.setLastname("Doe");

		User user = new User();
		user.setFirstname(userRequest.getFirstname());
		user.setLastname(userRequest.getLastname());

		when(userService.update(anyString(), any(User.class), anyString())).thenReturn(Mono.just(user));

		webTestClient
			.put()
			.uri("/users/{id}", userId)
			.header(HttpHeaders.AUTHORIZATION, authorization)
			.contentType(MediaType.APPLICATION_JSON)
	        .body(Mono.just(userRequest), UserRequest.class)
			.exchange()
			.expectStatus().isOk()
			.expectBody(UserResponse.class)
			.isEqualTo(UserResponse.fromUser(user));
	}
	
	@Test
	void updateMyAccount_shouldReturnUnauthorized() {
		String userId = "1";

		when(userService.update(anyString(), any(User.class), anyString())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));

		webTestClient
			.put()
			.uri("/users/{id}", userId)
			.contentType(MediaType.APPLICATION_JSON)
	        .body(Mono.just(new UserRequest()), UserRequest.class)
			.exchange()
			.expectStatus().isUnauthorized();
	}
	
	@Test
	@WithMockUser
	void updateMyAccountWithUnvalidToken_shouldReturnUnauthorized() {
		String userId = "1";
		String authorization = "Bearer invalid_token";

		UserRequest userRequest = new UserRequest();
		userRequest.setFirstname("John");
		userRequest.setLastname("Doe");

		User user = new User();
		user.setId("2");
		user.setFirstname(userRequest.getFirstname());
		user.setLastname(userRequest.getLastname());

		when(userService.update(anyString(), any(User.class), anyString())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to update this account.")));

		webTestClient
			.put()
			.uri("/users/{id}", userId)
			.header(HttpHeaders.AUTHORIZATION, authorization)
			.contentType(MediaType.APPLICATION_JSON)
	        .body(Mono.just(userRequest), UserRequest.class)
			.exchange()
			.expectStatus().isUnauthorized();
	}
	
	@Test
	@WithMockUser
	void updateMyAccount_shouldReturnUserIsNotFound() {
		String foreign_userId = "1";
		String authorization = "Bearer token";

		UserRequest userRequest = new UserRequest();
		userRequest.setFirstname("John");
		userRequest.setLastname("Doe");

		when(userService.update(anyString(), any(User.class), anyString())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found.")));

		webTestClient
			.put()
			.uri("/users/{id}", foreign_userId)
			.header(HttpHeaders.AUTHORIZATION, authorization)
			.contentType(MediaType.APPLICATION_JSON)
	        .body(Mono.just(userRequest), UserRequest.class)
			.exchange()
			.expectStatus().isNotFound();
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void updateUserForAdmin_shouldReturnUserForAdmin() {
		String userId = "1";
		String authorization = "Bearer token";
		
		User user = User.builder()
				.firstname("Jorge")
				.lastname("Lamda")
				.role(Role.USER)
				.build();

		UserRequest userRequest = new UserRequest();
		userRequest.setFirstname("John");
		userRequest.setLastname("Doe");
		
		user.setFirstname(userRequest.getFirstname());
		user.setLastname(userRequest.getLastname());

		when(userService.update(anyString(), any(User.class), anyString())).thenReturn(Mono.just(user));

		webTestClient
			.put()
			.uri("/users/{id}/admin", userId)
			.header(HttpHeaders.AUTHORIZATION, authorization)
			.contentType(MediaType.APPLICATION_JSON)
	        .body(Mono.just(userRequest), UserRequest.class)
			.exchange()
			.expectStatus().isOk()
			.expectBody(User.class)
			.isEqualTo(user);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void updateUserForAdmin_shouldReturnNotFound() {
		String not_existing_userId = "1";
		String authorization = "Bearer token";

		when(userService.update(anyString(), any(User.class), anyString())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found.")));

		webTestClient
			.put()
			.uri("/users/{id}/admin", not_existing_userId)
			.header(HttpHeaders.AUTHORIZATION, authorization)
			.contentType(MediaType.APPLICATION_JSON)
	        .body(Mono.just(new UserRequest()), UserRequest.class)
			.exchange()
			.expectStatus().isNotFound();
	}
	
	@Test
	void updateUserForAdmin_shouldReturnUnauthorized() {
		webTestClient
			.put()
			.uri("/users/{id}/admin", "1")
			.contentType(MediaType.APPLICATION_JSON)
	        .body(Mono.just(new UserRequest()), UserRequest.class)
			.exchange()
			.expectStatus().isUnauthorized();
	}
	
	@Test
	@WithMockUser
	void postUserProfilePhoto_shouldReturnUserProfilePhoto() {
	    FilePart filePart = mock(FilePart.class);

	    byte[] imageData = "test".getBytes(StandardCharsets.UTF_8);
	    DefaultDataBuffer dataBuffer = DefaultDataBufferFactory.sharedInstance.wrap(ByteBuffer.wrap(imageData));
	    Flux<DataBuffer> body = Flux.just(dataBuffer);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG);

	    Image image = new Image();
	    image.setContentType(MediaType.IMAGE_JPEG_VALUE);
	    image.setTitle("test.jpg");
	    image.setImage(new Binary(imageData));

	    when(filePart.headers()).thenReturn(headers);
	    when(filePart.filename()).thenReturn("test.jpg");
	    when(filePart.content()).thenReturn(body);
	    when(userService.addProfilePhoto(any(Mono.class), anyString())).thenReturn(Mono.just(image));

	    webTestClient = WebTestClient.bindToController(userController).build();
		
		webTestClient.post()
	        .uri("/users/profile-photo")
	        .header("Content-Length", String.valueOf(imageData.length))
	        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
	        .contentType(MediaType.MULTIPART_FORM_DATA)
	        .body(BodyInserters.fromMultipartData("file", filePart))
	        .exchange()
	        .expectStatus().isOk()
	        .expectHeader().contentType(MediaType.IMAGE_JPEG)
	        .expectHeader().contentDisposition(ContentDisposition.builder("attachment")
	                .filename("test.jpg").build())
	        .expectBody(byte[].class)
	        .value(response -> assertArrayEquals(response, image.getImage().getData()));
	}
	
	@Test
	void postUserProfilePhotoEmptyToken_shouldReturnUnauthorized() {
	    FilePart filePart = mock(FilePart.class);
	    byte[] imageData = "test".getBytes(StandardCharsets.UTF_8);
	    DefaultDataBuffer dataBuffer = DefaultDataBufferFactory.sharedInstance.wrap(ByteBuffer.wrap(imageData));
	    Flux<DataBuffer> body = Flux.just(dataBuffer);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG);
	    
	    when(filePart.headers()).thenReturn(headers);
	    when(filePart.filename()).thenReturn("test.jpg");
	    when(filePart.content()).thenReturn(body);
	    
	    when(userService.addProfilePhoto(any(Mono.class), anyString())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));

	    webTestClient = WebTestClient.bindToController(userController).build();
		
		webTestClient.post()
	        .uri("/users/profile-photo")
	        .header("Content-Length", "4645766")
	        .header(HttpHeaders.AUTHORIZATION, "Bearer ")
	        .contentType(MediaType.MULTIPART_FORM_DATA)
	        .body(BodyInserters.fromMultipartData("file", filePart))
	        .exchange()
	        .expectStatus().isUnauthorized();
	}
	
	@Test
	void postUserProfilePhotoOfForeignUser_shouldReturnUnauthorized() {
	    FilePart filePart = mock(FilePart.class);
	    byte[] imageData = "test".getBytes(StandardCharsets.UTF_8);
	    DefaultDataBuffer dataBuffer = DefaultDataBufferFactory.sharedInstance.wrap(ByteBuffer.wrap(imageData));
	    Flux<DataBuffer> body = Flux.just(dataBuffer);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG);
	    
	    when(filePart.headers()).thenReturn(headers);
	    when(filePart.filename()).thenReturn("test.jpg");
	    when(filePart.content()).thenReturn(body);
	    
	    when(userService.addProfilePhoto(any(Mono.class), anyString())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));

	    webTestClient = WebTestClient.bindToController(userController).build();
		
		webTestClient.post()
	        .uri("/users/profile-photo")
	        .header("Content-Length", "4645766")
	        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
	        .contentType(MediaType.MULTIPART_FORM_DATA)
	        .body(BodyInserters.fromMultipartData("file", filePart))
	        .exchange()
	        .expectStatus().isUnauthorized();
	}
	
	@Test
	void postUserProfilePhoto_shouldReturnInternalServerError() {
	    FilePart filePart = mock(FilePart.class);
	    byte[] imageData = "test".getBytes(StandardCharsets.UTF_8);
	    DefaultDataBuffer dataBuffer = DefaultDataBufferFactory.sharedInstance.wrap(ByteBuffer.wrap(imageData));
	    Flux<DataBuffer> body = Flux.just(dataBuffer);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG);
	    
	    when(filePart.headers()).thenReturn(headers);
	    when(filePart.filename()).thenReturn("test.jpg");
	    when(filePart.content()).thenReturn(body);
	    
	    when(userService.addProfilePhoto(any(Mono.class), anyString())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to insert profile photo.")));

	    webTestClient = WebTestClient.bindToController(userController).build();
		
		webTestClient.post()
	        .uri("/users/profile-photo")
	        .header("Content-Length", "4645766")
	        .header(HttpHeaders.AUTHORIZATION, "Bearer token")
	        .contentType(MediaType.MULTIPART_FORM_DATA)
	        .body(BodyInserters.fromMultipartData("file", filePart))
	        .exchange()
	        .expectStatus().is5xxServerError();
	}
	
	@Test
    public void getProfilePhotoReturnsImage() {
        Image image = Image.builder()
	        .id("imageId")
	        .title("image.jpg")
	        .contentType("image/jpeg")
	        .image(new Binary(new byte[]{}))
	        .build();
        
        when(userService.getProfilePhotoById(anyString())).thenReturn(Mono.just(image));

        // Perform the request and verify the response
        webTestClient.get()
                .uri("/users/{id}/profile-photo", "imageId")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.valueOf("image/jpeg"))
                .expectHeader().valueEquals("Content-Disposition", "attachment; filename=image.jpg")
                .expectBody(byte[].class).isEqualTo(image.getImage().getData());
    }
	
	@Test
    public void getProfilePhotoReturnsUserNotFound() {
		String id = "imageId";
        
        when(userService.getProfilePhotoById(anyString())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " is not found.")));

        // Perform the request and verify the response
        webTestClient.get()
                .uri("/users/{id}/profile-photo", id)
                .exchange()
                .expectStatus().isNotFound();
    }
	
	@Test
    public void getProfilePhotoReturnsProfilePhotoNotFound() {
		String id = "imageId";
        
        when(userService.getProfilePhotoById(anyString())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " has no profile photo.")));

        // Perform the request and verify the response
        webTestClient.get()
                .uri("/users/{id}/profile-photo", id)
                .exchange()
                .expectStatus().isNotFound();
    }
	
	@Test
	@WithMockUser
	void deleteUser_shouldDeleteUserAndReturnNoContent() {
		String userId = "1";
		String authorization = "Bearer token";

		when(userService.delete(userId, authorization)).thenReturn(Mono.empty());

		 webTestClient.delete()
			.uri("/users/{id}", userId)
			.header(HttpHeaders.AUTHORIZATION, authorization)
			.exchange()
			.expectStatus().isNoContent();
	}
	
	@Test
	void deleteUser_shouldDeleteUserAndReturnUnauthorized() {
		String userId = "1";

		webTestClient.delete()
			.uri("/users/{id}", userId)
			.exchange()
			.expectStatus().isUnauthorized();
	}
	
	@Test
	@WithMockUser
	void deleteForeignUser_shouldDeleteUserAndReturnUnauthorized() {
		String userId = "1";
		String authorization = "Bearer token";

		when(userService.delete(userId, authorization)).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to delete this account.")));

		 webTestClient.delete()
			.uri("/users/{id}", userId)
			.header(HttpHeaders.AUTHORIZATION, authorization)
			.exchange()
			.expectStatus().isUnauthorized();
	}
	
	@Test
	@WithMockUser
	void deleteUser_shouldDeleteUserAndReturnNotFound() {
		String userId = "1";
		String authorization = "Bearer token";

		when(userService.delete(userId, authorization))
				.thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found.")));

		webTestClient.delete()
			.uri("/users/{id}", userId)
			.header(HttpHeaders.AUTHORIZATION, authorization)
			.exchange()
			.expectStatus().isNotFound();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
    void deleteAllUsers_shouldDeleteAllUsers() {
        when(userService.deleteAll()).thenReturn(Mono.empty());

        webTestClient.delete()
			.uri("/users")
			.exchange()
			.expectStatus().isNoContent();
    }
	
	@Test
    void deleteAllUsers_shouldReturnNotFound() {
        webTestClient.delete()
			.uri("/users")
			.exchange()
			.expectStatus().isUnauthorized();
    }
	
	@Test
	@WithMockUser
    void deleteAllUsers_shouldReturnForbitten() {
        webTestClient.delete()
			.uri("/users")
			.exchange()
			.expectStatus().isForbidden();
    }
}
