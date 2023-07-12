package com.artconnect.backend.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.model.Image;
import com.artconnect.backend.model.user.Role;
import com.artconnect.backend.model.user.User;
import com.artconnect.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	private final ImageService imageService;
	
	@Autowired
    @Lazy
	private GalleryService galleryService;
	
	private final JwtService jwtService;
	
	private final PasswordEncoder pEncoder;
	

	public Flux<User> findAll() {
		return userRepository.findAll();
	}
	
	public Flux<User> findByFirstname(String firstname) {
		return userRepository.findByFirstnameIgnoreCase(firstname);
	}
	
	public Flux<User> findByLastname(String lastname) {
		return userRepository.findByLastnameIgnoreCase(lastname);
	}

	public Flux<User> findByFirstnameAndLastname(String firstname, String lastname) {
		return userRepository.findByFirstnameIgnoreCaseAndLastnameIgnoreCase(firstname, lastname);
	}
	
	public Mono<User> findById(String id) {
		return userRepository.findById(id)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found.")));
	}
	
	public Mono<User> findByEmail(String email) {
		return userRepository.findByEmail(email)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found.")));
	}
	
	public Mono<User> getCurrentUser() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User)
                .map(authentication -> (org.springframework.security.core.userdetails.User) authentication.getPrincipal())
                .flatMap(user -> findByEmail(user.getUsername()));
    }

	public Mono<User> create(User user) {
		user.setCreatedAt(new Date());
		user.setPassword(pEncoder.encode(user.getPassword()));
		return userRepository.save(user).onErrorResume(IllegalArgumentException.class, error -> Mono
				.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error by creating user")));
	}
	
	public Mono<User> update(String id, User user, String authorization) {  
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED));
		}
		String token = authorization.substring(7);
		String userEmail = jwtService.extractUsername(token);

	    return userRepository.findByEmail(userEmail)
	            .flatMap(authUser -> {
	                if (authUser.getId().equals(id) || authUser.getRole() == Role.ADMIN) {
	                	return userRepository.findById(id).flatMap(foundUser -> {
	                		if(authUser.getRole() == Role.ADMIN) {
	                			Optional.ofNullable(user.getId()).ifPresent(foundUser::setId);
	                			Optional.ofNullable(user.getEmail()).ifPresent(foundUser::setEmail);
	                			Optional.ofNullable(user.getPassword()).ifPresent(password -> {
	                				if (!user.getPassword().equals(foundUser.getPassword())) {
		                                foundUser.setPassword(pEncoder.encode(user.getPassword()));
		                            }
	                			});
	                			Optional.ofNullable(user.getCreatedAt()).ifPresent(foundUser::setCreatedAt);
	                			Optional.ofNullable(user.getIsAccountEnabled()).ifPresent(foundUser::setIsAccountEnabled);
	                			Optional.ofNullable(user.getRole()).ifPresent(foundUser::setRole);
	                			Optional.ofNullable(user.getProfilePhoto()).ifPresent(foundUser::setProfilePhoto);
	                		}	
	    	                Optional.ofNullable(user.getFirstname()).ifPresent(foundUser::setFirstname);
	    	                Optional.ofNullable(user.getLastname()).ifPresent(foundUser::setLastname);              	    	                
	    	                Optional.ofNullable(user.getDateOfBirthday()).ifPresent(foundUser::setDateOfBirthday);
	    	                Optional.ofNullable(user.getIsDateOfBirthVisible()).ifPresent(foundUser::setIsDateOfBirthVisible);	    	                	    	               	    	               	    	                
	    	                Optional.ofNullable(user.getBiography()).ifPresent(foundUser::setBiography);
	    	                Optional.ofNullable(user.getExhibitions()).ifPresent(foundUser::setExhibitions);
	    	                Optional.ofNullable(user.getContacts()).ifPresent(foundUser::setContacts);
	    	                Optional.ofNullable(user.getSocialMedias()).ifPresent(foundUser::setSocialMedias);    	
                			Optional.ofNullable(user.getGalleryId()).ifPresent(foundUser::setGalleryId);
	    					return userRepository.save(foundUser);          				
	                	});
	                } else {
	                	return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to update this account."));
	                }
	            })
	            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found.")));
	}

	public Mono<Image> addProfilePhoto(Mono<FilePart> file, String authorization) {
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED));
		}
		String token = authorization.substring(7);
		String userEmail = jwtService.extractUsername(token);
		return userRepository.findByEmail(userEmail)
				.flatMap(user -> {
					return imageService.addPhoto(file)
							.flatMap(image -> {
								String oldProfilePhotoId = user.getProfilePhoto() != null ? user.getProfilePhoto().getId() : "";
								user.setProfilePhoto(image);
								return userRepository.save(user)
										.then(imageService.deleteById(oldProfilePhotoId))
										.thenReturn(image);
							})
							.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to insert profile photo.")));
				}).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED)));
	}

	public Mono<Image> getProfilePhotoById(String id) {
		return userRepository.findById(id).map(user -> {
			return user.getProfilePhoto();
		})
		.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " is not found.")))
		.onErrorResume(NullPointerException.class,
						err -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
								"User with id " + id + " has no profile photo.")));
	}

	public Mono<Void> delete(String id, String authorization) {
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED));
		}
		String token = authorization.substring(7);
		String userEmail = jwtService.extractUsername(token);

	    return userRepository.findByEmail(userEmail)
	    		.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not found.")))
	            .flatMap(foundUser -> {
	            	 if (foundUser.getId().equals(id) || foundUser.getRole() == Role.ADMIN) {
	            		 Mono<Void> deleteProfilePhoto = Mono.empty();
	                     if (foundUser.getProfilePhoto() != null) {
	                         deleteProfilePhoto = imageService.deleteById(foundUser.getProfilePhoto().getId());
	                     }

	                     Mono<Void> deleteGallery = Mono.empty();
	                     if (foundUser.getGalleryId() != null) {
	                         deleteGallery = galleryService.delete(foundUser.getGalleryId(), authorization);
	                     }

	                     return Mono.when(deleteProfilePhoto, deleteGallery)
	                             .then(userRepository.deleteById(id));
	                 } else {
	                     return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to delete this account."));
	                 }
	            });
	}

	public Mono<Void> deleteAll() {
		return userRepository.deleteAll();
	}

}
