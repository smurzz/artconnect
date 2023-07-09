package com.artconnect.backend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.controller.response.ArtWorkResponse;
import com.artconnect.backend.controller.response.GalleryResponse;
import com.artconnect.backend.model.gallery.Gallery;
import com.artconnect.backend.model.user.Role;
import com.artconnect.backend.repository.GalleryRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GalleryService {
	
	public final GalleryRepository galleryRepository;
	
	private final JwtService jwtService;
	
	private final UserService userService;
	
	private final ArtWorkService artWorkService;
	
	private final ImageService imageService;
		
	public Flux<Gallery> findAll() {
		return galleryRepository.findAll();
	}
	
	public Mono<Gallery> findById(String id) {
		return galleryRepository.findById(id)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Gallery with id <" + id + "> is not found.")));
	}

	public Mono<Gallery> create(Gallery gallery, String authorization) {
		String userEmail = getEmailFromAuthentication(authorization);
	    return userService.findByEmail(userEmail)
	            .filter(user -> user.getEmail().equals(userEmail))
	            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to create a gallery for another user")))
	            .flatMap(user -> {
	                String ownerFullName = user.getFirstname() + " " + user.getLastname();
	                gallery.setOwnerId(user.getId());
	                gallery.setOwnerName(ownerFullName);
	                return galleryRepository.save(gallery)
	                        .doOnSuccess(savedGallery -> {
	                            user.setGalleryId(savedGallery.getId());
	                            userService.update(user.getId(), user, authorization).subscribe();	                         
	                        })
	                        .onErrorResume(IllegalArgumentException.class, error ->
	                                Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error by creating gallery")));
	            });
	}
	
	public Mono<Gallery> update(String id, Gallery gallery, String authorization) {
		return userService.findByEmail(getEmailFromAuthentication(authorization))
				.flatMap(user -> {
					return findById(id)
							.filter(existingGallery -> existingGallery.getOwnerId().equals(user.getId()) || user.getRole() == Role.ADMIN)
							.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update a gallery for another user")))
							.flatMap(existingGallery -> {
								if(user.getRole() == Role.ADMIN) {
									Optional.ofNullable(gallery.getId()).ifPresent(existingGallery::setId);
									Optional.ofNullable(gallery.getOwnerId()).ifPresent(existingGallery::setOwnerId);
									Optional.ofNullable(gallery.getArtworkIds()).ifPresent(existingGallery::setArtworkIds);
									Optional.ofNullable(gallery.getEvaluations()).ifPresent(existingGallery::setEvaluations);
									Optional.ofNullable(gallery.getOwnerName()).ifPresent(existingGallery::setOwnerName);
								}
								Optional.ofNullable(gallery.getDescription()).ifPresent(existingGallery::setDescription);
								Optional.ofNullable(gallery.getCategories()).ifPresent(existingGallery::setCategories);
								Optional.ofNullable(gallery.getTitle()).ifPresent(title -> {
								    if (title.isEmpty()) {
								        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title cannot be empty.");
								    }
								    existingGallery.setTitle(title);
								});
								return galleryRepository.save(existingGallery);
							});
		});
	}
	
	public Mono<Void> delete(String id, String authorization) {
	    return userService.findByEmail(getEmailFromAuthentication(authorization))
	            .flatMap(user -> findById(id)
	                    .filter(existingGallery -> existingGallery.getOwnerId().equals(user.getId()) || user.getRole() == Role.ADMIN)
	                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete a gallery for another user")))
	                    .flatMap(existingGallery -> {
	                        List<String> artworkIds = (existingGallery.getArtworkIds() == null) ? new ArrayList<>() : existingGallery.getArtworkIds();
	                        if (artworkIds.isEmpty() || artworkIds == null) {
	                        	 return galleryRepository.delete(existingGallery)
	                        	            .then(userService.findById(user.getId()))
	                        	            .flatMap(existingUser -> {
	                        	                existingUser.setGalleryId("");
	                        	                return userService.update(existingUser.getId(), existingUser, authorization).then();
	                        	            });	              
	                        } else {
	                        	return artWorkService.deleteAllByIds(existingGallery.getArtworkIds(), authorization)
	                                    .then(galleryRepository.delete(existingGallery))
	                                    .then(userService.findById(user.getId()))
	                                    .flatMap(existingUser -> {
	                                        existingUser.setGalleryId("");
	                                        return userService.update(existingUser.getId(), existingUser, authorization).then();
	                                    });
	                        }	                        
	                    }))
	                    .then();
	}
	
	public Mono<GalleryResponse> mapGalleryToResponse(Gallery gallery) {
	    return artWorkService.findByGalleryId(gallery.getId())
	            .flatMap(artwork -> {
	                List<String> imageIds = artwork.getImagesIds();
	                if (imageIds != null && !imageIds.isEmpty()) {
	        	        return imageService.getPhotosByIds(imageIds)
	        	                .collectList()
	        	                .map(images -> ArtWorkResponse.fromArtWork(artwork, images));
	        	    } else {
	        	        return Mono.just(ArtWorkResponse.fromArtWork(artwork, Collections.emptyList()));
	        	    }	                             
	            })
	            .collectList()
	            .map(artworkResponses -> GalleryResponse.fromGallery(gallery, artworkResponses));
	}

	private String getEmailFromAuthentication(String authorization) {
		String token = authorization.substring(7);
	    return jwtService.extractUsername(token);
	}

}
