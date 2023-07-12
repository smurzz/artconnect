package com.artconnect.backend.service;

import org.springframework.stereotype.Service;

import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.model.gallery.Gallery;
import com.artconnect.backend.repository.GalleryRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RatingService {
	
	public final GalleryRepository galleryRepository;
	
	private final UserService userService;
	
	private final JwtService jwtService;
	
	private final GalleryService galleryService;

	public Mono<Gallery> addRating(String id, Integer value, String authorization) {
		return userService.findByEmail(getEmailFromAuthentication(authorization))
				.flatMap(user -> {
					return galleryService.findById(id)
							.flatMap(existingGallery -> {
								existingGallery.setEvaluation(user.getId(), value);
								return galleryRepository.save(existingGallery);
							});
				});		
	}
	
	private String getEmailFromAuthentication(String authorization) {
		String token = authorization.substring(7);
	    return jwtService.extractUsername(token);
	}

}
