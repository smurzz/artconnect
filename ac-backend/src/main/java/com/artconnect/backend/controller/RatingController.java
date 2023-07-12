package com.artconnect.backend.controller;

import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.artconnect.backend.controller.response.GalleryResponse;
import com.artconnect.backend.model.gallery.Gallery;
import com.artconnect.backend.service.GalleryService;
import com.artconnect.backend.service.RatingService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/galleries/{id}/rating")
@RequiredArgsConstructor
@Validated
public class RatingController {
	
	private final GalleryService galleryService;
	
	private final RatingService ratingService;
	
	@PostMapping
	public Mono<GalleryResponse> addRatingToGallery(
			@PathVariable("id") String id, 
			@RequestParam @Range(min = 1, max = 5) Integer value,
			@RequestHeader("Authorization") String authorization) {
		return ratingService.addRating(id, value, authorization).flatMap(this::mapGalleryToResponse);
	}
	
	private Mono<GalleryResponse> mapGalleryToResponse(Gallery gallery) {
        return galleryService.mapGalleryToResponse(gallery);
    }

}
