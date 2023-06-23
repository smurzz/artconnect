package com.artconnect.backend.controller;

import java.io.IOException;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.artconnect.backend.controller.request.UserRequest;
import com.artconnect.backend.controller.response.UserResponse;
import com.artconnect.backend.model.user.User;
import com.artconnect.backend.service.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

	private final UserService userService;

	@GetMapping("/")
	public Flux<UserResponse> getAllUsers() {
		return userService.findAll()
				.map(user -> UserResponse.fromUser(user));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
    public Flux<User> getUsersForAdmin() {
        return userService.findAll();
    }

	@GetMapping("/{id}")
	public Mono<UserResponse> getUserById(@PathVariable("id") String id) {
		return userService.findById(id)
				.map(user -> UserResponse.fromUser(user));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}/admin")
	public Mono<User> getUserByIdForAdmin(@PathVariable("id") String id) {
		return userService.findById(id);
	}
	
	@GetMapping
	public Mono<UserResponse> getUserByEmail(@RequestParam(value="email") String email) {
		return userService.findByEmail(email)
				.map(user -> UserResponse.fromUser(user));
	}

	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<User> createUser(@RequestBody UserRequest userRequest) {
		User user = User.builder()
				.id(userRequest.getId())
				.firstname(userRequest.getFirstname())
				.lastname(userRequest.getLastname())
				.email(userRequest.getEmail())
				.password(userRequest.getPassword())
				.role(userRequest.getRole())
				.profilePhoto(userRequest.getProfilePhoto())
				.isAccountEnabled(userRequest.getIsAccountEnabled())
				.createdAt(new Date())
				.dateOfBirthday(userRequest.getDateOfBirthday())
				.isDateOfBirthVisible(userRequest.getIsDateOfBirthVisible())
				.biography(userRequest.getBiography())
				.exhibitions(userRequest.getExhibitions())
				.contacts(userRequest.getContacts())
				.socialMedias(userRequest.getSocialMedias())
				.build();
		return userService.create(user);
	}
	
	@PutMapping("/{id}")
	public Mono<UserResponse> updateMyAccount(
			@PathVariable("id") String id, 
			@RequestBody UserRequest userRequest, 
			@RequestHeader("Authorization") String authorization){
		User user = User.builder()
				.firstname(userRequest.getFirstname())
				.lastname(userRequest.getLastname())
				.dateOfBirthday(userRequest.getDateOfBirthday())
				.isDateOfBirthVisible(userRequest.getIsDateOfBirthVisible())
				.biography(userRequest.getBiography())
				.exhibitions(userRequest.getExhibitions())
				.contacts(userRequest.getContacts())
				.socialMedias(userRequest.getSocialMedias())
				.build();
		return userService.update(id, user, authorization)
				.map(updatedUser -> UserResponse.fromUser(updatedUser));
	}
	
	@PutMapping("/{id}/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public Mono<User> updateUserByAdmin(
			@PathVariable("id") String id, 
			@RequestBody UserRequest userRequest,
			@RequestHeader("Authorization") String authorization) {
		User user = User.builder()
				.id(userRequest.getId())
				.firstname(userRequest.getFirstname())
				.lastname(userRequest.getLastname())
				.email(userRequest.getEmail())
				.password(userRequest.getPassword())
				.role(userRequest.getRole())
				.profilePhoto(userRequest.getProfilePhoto())
				.isAccountEnabled(userRequest.getIsAccountEnabled())
				.createdAt(new Date())
				.dateOfBirthday(userRequest.getDateOfBirthday())
				.isDateOfBirthVisible(userRequest.getIsDateOfBirthVisible())
				.biography(userRequest.getBiography())
				.exhibitions(userRequest.getExhibitions())
				.contacts(userRequest.getContacts())
				.socialMedias(userRequest.getSocialMedias())
				.build();
		return userService.update(id, user, authorization);
	}

	@PostMapping(value = "/profile-photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<ResponseEntity<byte[]>> addProfilePhoto(
			@RequestPart Mono<FilePart> file, 
			@RequestHeader("Content-Length") Long fileSize,
			@RequestHeader("Authorization") String authorization) {
		return userService.addProfilePhoto(file, fileSize, authorization)
				.map(image -> {
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.valueOf(image.getContentType()));
					headers.set("Content-Disposition", "attachment; filename=" + image.getTitle());
		
					return ResponseEntity.ok().headers(headers).body(image.getImage().getData());
		});
	}
	
	@GetMapping("/{id}/profile-photo")
	public Mono<ResponseEntity<byte[]>> getUserProfilePhotoById (@PathVariable String id){
		return userService.getProfilePhotoById(id)
				.map(image -> {
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.valueOf(image.getContentType()));
					headers.set("Content-Disposition", "attachment; filename=" + image.getTitle());
		
					return ResponseEntity.ok().headers(headers).body(image.getImage().getData());
		});
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> deleteUser(@PathVariable String id, @RequestHeader("Authorization") String authorization) {
	    return userService.delete(id, authorization);
	}

	@DeleteMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> deleteAllUsers() {
		return userService.deleteAll();
	}
}
