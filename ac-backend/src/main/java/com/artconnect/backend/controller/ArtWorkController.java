package com.artconnect.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
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

import com.artconnect.backend.controller.request.ArtWorkRequest;
import com.artconnect.backend.controller.request.ArtWorkUpdateRequest;
import com.artconnect.backend.controller.response.ArtWorkResponse;
import com.artconnect.backend.model.artwork.ArtDirection;
import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.service.ArtWorkService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/artworks")
@RequiredArgsConstructor
@Validated
public class ArtWorkController {
	
	private final ArtWorkService artWorkService;
	
	@GetMapping
	public Flux<ArtWorkResponse> getAllArtWorks() {
		return artWorkService.findAll().flatMap(this::mapArtWorkToResponse);
	}
	
	@GetMapping("/search")
	public Flux<ArtWorkResponse> getArtworksByParam(
			@RequestParam(required = false) String ownerId,
	        @RequestParam(required = false) String galleryId,
	        @RequestParam(required = false) String ownerName,
	        @RequestParam(required = false) List<String> materials,
	        @RequestParam(required = false) List<String> tags,
	        @RequestParam(required = false) List<ArtDirection> artDirections,
	        @RequestParam(required = false) Double priceLessThan,
	        @RequestParam(required = false) Double maxPrice,
	        @RequestParam(required = false) Double minPrice) {
		if (ownerId != null) {
			return artWorkService.findByOwnerId(ownerId).flatMap(this::mapArtWorkToResponse);
		} else if (galleryId != null) {
			return artWorkService.findByGalleryId(galleryId).flatMap(this::mapArtWorkToResponse);
		} else if (ownerName != null) {
			return artWorkService.findByOwnerName(ownerName).flatMap(this::mapArtWorkToResponse);
		} else if (materials != null && materials.size() != 0) {
			return artWorkService.findByMaterialsIn(materials).flatMap(this::mapArtWorkToResponse);
		} else if (tags != null && tags.size() != 0) {
			return artWorkService.findByTagsIn(tags).flatMap(this::mapArtWorkToResponse);
		} else if (artDirections != null && artDirections.size() != 0) {
			return artWorkService.findByArtDirectionsIn(artDirections).flatMap(this::mapArtWorkToResponse);
		} else if (priceLessThan != null) {
			return artWorkService.findByPriceLessThan(priceLessThan).flatMap(this::mapArtWorkToResponse);
		} else if (minPrice != null && maxPrice != null) {
			return artWorkService.findByPriceBetween(minPrice, maxPrice).flatMap(this::mapArtWorkToResponse);
		} else {
			return Flux.empty();
		}
	}
	
	@GetMapping("/{id}")
	public Mono<ArtWorkResponse> getArtWorkById(@PathVariable("id") String id) {
		return artWorkService.findById(id).flatMap(this::mapArtWorkToResponse);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<ArtWorkResponse> createArtWork(
			@RequestHeader("Authorization") String authorization,
			@Valid @RequestBody ArtWorkRequest artWorkRequest) {
		ArtWork artwork = ArtWork.builder()
				.title(artWorkRequest.getTitle())
				.description(artWorkRequest.getDescription())
				.yearOfCreation(artWorkRequest.getYearOfCreation())
				.materials(artWorkRequest.getMaterials())
				.tags(artWorkRequest.getTags())
				.artDirections(artWorkRequest.getArtDirections())
				.dimension(artWorkRequest.getDimension())
				.price(artWorkRequest.getPrice())
				.location(artWorkRequest.getLocation())
				.build();
		return artWorkService.create(artwork, authorization).flatMap(this::mapArtWorkToResponse);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/add-photo/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Mono<String> addPhotoToArtWork(
			@PathVariable("id") String id,
			@RequestPart Mono<FilePart> file,
			@RequestHeader("Authorization") String authorization) {
	    return artWorkService.addImage(id, file, authorization);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/add-photos/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Mono<String> addPhotosToArtWork(
			@PathVariable("id") String id,
	        @RequestPart Flux<FilePart> files,
	        @RequestHeader("Authorization") String authorization) {		
		 return artWorkService.addImages(id, files, authorization)
				 .map(imageNum -> "Images saved for ArtWork.");
	}
	
	@PutMapping("/{id}")
	public Mono<ArtWorkResponse> updateMyArtWork(
			@PathVariable("id") String id, 
			@Valid @RequestBody ArtWorkUpdateRequest artworkRequest, 
			@RequestHeader("Authorization") String authorization){
		ArtWork artWork = ArtWorkUpdateRequest.toArtWork(artworkRequest);
		return artWorkService.update(id, artWork, authorization).flatMap(this::mapArtWorkToResponse);
	}
	
	@PostMapping("/{id}/like")
	public Mono<ArtWorkResponse> addRemoveLikeByArtWork(
			@PathVariable("id") String id, 
			@RequestHeader("Authorization") String authorization) {
		return artWorkService.addRemoveLike(id, authorization).flatMap(this::mapArtWorkToResponse);
	}
	
	@DeleteMapping("/{id}/images/{imageId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> deleteImageInArtWorkById(@PathVariable String id, @PathVariable String imageId, @RequestHeader("Authorization") String authorization) {
	    return artWorkService.deleteImageById(id, imageId, authorization);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> deleteArtWork(@PathVariable String id, @RequestHeader("Authorization") String authorization) {
	    return artWorkService.delete(id, authorization);
	}
	
	private Mono<ArtWorkResponse> mapArtWorkToResponse(ArtWork artWork) {
        return artWorkService.mapArtWorkToResponse(artWork);
    }
	
}
