package com.artconnect.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.artconnect.backend.controller.request.GalleryRequest;
import com.artconnect.backend.controller.response.GalleryResponse;
import com.artconnect.backend.model.gallery.Gallery;
import com.artconnect.backend.model.gallery.GalleryCategory;
import com.artconnect.backend.service.GalleryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/galleries")
@RequiredArgsConstructor
@Validated
public class GalleryController {
	
	private final GalleryService galleryService;
	
	@GetMapping
	public Flux<GalleryResponse> getAllGalleries() {
		return galleryService.findAll().flatMap(this::mapGalleryToResponse);
	}
	
	@GetMapping("/search")
	public Flux<GalleryResponse> getGalleriesByParam(
	        @RequestParam(required = false) List<GalleryCategory> galleryCategories) {
		if (galleryCategories != null && galleryCategories.size() != 0) {
			return galleryService.findByCategoriesIn(galleryCategories).flatMap(this::mapGalleryToResponse);
		} else {
			return Flux.empty();
		}
	}
	
	@GetMapping("/{id}")
	public Mono<GalleryResponse> getGalleryById(@PathVariable("id") String id) {
		return galleryService.findById(id).flatMap(this::mapGalleryToResponse);
	}
	
	@GetMapping("/myGallery")
	@PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
	public Mono<GalleryResponse> getMyGallery() {
		return galleryService.findMyGallery().flatMap(this::mapGalleryToResponse);
	}
	
	@GetMapping("/user/{id}")
	public Mono<GalleryResponse> getGalleryByOwner(@PathVariable("id") String userId) {
		return galleryService.findByOwnerId(userId).flatMap(this::mapGalleryToResponse);
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<GalleryResponse> createGallery(
			@RequestHeader("Authorization") String authorization,
			@Valid @RequestBody GalleryRequest galleryRequest) {
		Gallery gallery = Gallery.builder()
				.title(galleryRequest.getTitle())
				.description(galleryRequest.getDescription())
				.categories(galleryRequest.getCategories())
				.build();
		return galleryService.create(gallery, authorization).flatMap(this::mapGalleryToResponse);
	}
	
	@PutMapping("/{id}")
	public Mono<GalleryResponse> updateMyGallery(
			@PathVariable("id") String id, 
			@RequestBody GalleryRequest galleryRequest, 
			@RequestHeader("Authorization") String authorization){
		Gallery gallery = Gallery.builder()
				.id(galleryRequest.getId())
				.ownerId(galleryRequest.getOwnerId())
				.ownerName(galleryRequest.getOwnerName())
				.title(galleryRequest.getTitle())
				.description(galleryRequest.getDescription())
				.artworkIds(galleryRequest.getArtworkIds())
				.categories(galleryRequest.getCategories())
				.evaluations(galleryRequest.getEvaluations())
				.build();
		return galleryService.update(id, gallery, authorization).flatMap(this::mapGalleryToResponse);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> deleteGallery(@PathVariable String id, @RequestHeader("Authorization") String authorization) {
	    return galleryService.delete(id, authorization);
	}
	
	private Mono<GalleryResponse> mapGalleryToResponse(Gallery gallery) {
        return galleryService.mapGalleryToResponse(gallery);
    }
	
}
