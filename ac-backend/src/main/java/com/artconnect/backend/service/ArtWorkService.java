package com.artconnect.backend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.repository.ArtWorkRepository;
import com.artconnect.backend.repository.GalleryRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ArtWorkService {
	
	private final ArtWorkRepository artWorkRepository;
	
	private final GalleryRepository galleryRepository;
	
	private final JwtService jwtService;
	
	private final UserService userService;
	
	private final ImageService imageService;
	
	// private final GalleryService galleryService;

	public Flux<ArtWork> findAll() {
		return artWorkRepository.findAll();
	}
	
	public Flux<ArtWork> findByOwnerId(String ownerId) {
		return artWorkRepository.findByOwnerId(ownerId);
	}
	
	public Flux<ArtWork> findByOwnerName(String ownerName) {
		return artWorkRepository.findByOwnerName(ownerName);
	}
	
	public Flux<ArtWork> findByGalleryId(String galleryId) {
		return artWorkRepository.findByGalleryId(galleryId);
	}
	
	public Flux<ArtWork> findByMaterialsIn(List<String> materials) {
		return artWorkRepository.findByMaterialsIn(materials);
	}
	
	public Flux<ArtWork> findByPriceLessThan(Double price) {
		return artWorkRepository.findByPriceLessThan(price);
	}
	
	public Flux<ArtWork> findByPriceBetween(Double minPrice, Double maxPrice) {
		return artWorkRepository.findByPriceBetween(minPrice, maxPrice);
	}

	public Mono<ArtWork> findById(String id) {
		return artWorkRepository.findById(id)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Artwork is not found.")));
	}

	public Mono<ArtWork> create(ArtWork artwork, String authorization) {
	    return userService.findByEmail(getEmailFromAuthentication(authorization))
	            .flatMap(user -> {
	                String userGalleryId = user.getGalleryId();
	                if (userGalleryId != null) {
	                    return galleryRepository.findById(userGalleryId)
	                            .filter(gallery -> gallery.getOwnerId().equals(user.getId()))
	                            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to create an artwork for another user or gallery")))
	                            .flatMap(gallery -> {
	                                artwork.setOwnerId(user.getId());
	                                artwork.setOwnerName(user.getFirstname() + " " + user.getLastname());
	                                artwork.setCreatedAt(new Date());
	                                artwork.setGalleryId(gallery.getId());
	                                artwork.setGalleryTitle(gallery.getTitle());
	                                return artWorkRepository.save(artwork)
	                                        .doOnSuccess(savedArtwork -> {
	                                            List<String> artworkIdsList = (gallery.getArtworkIds() == null) ? new ArrayList<>() : gallery.getArtworkIds();
	                                            artworkIdsList.add(savedArtwork.getId());
	                                            gallery.setArtworkIds(artworkIdsList);
	                                            galleryRepository.save(gallery);
	                                        })
	                                        .onErrorResume(IllegalArgumentException.class, error ->
	                                                Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error by creating artwork")));
	                            });
	                } else {
	                    return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't have a gallery."));
	                }
	            });        
	}


	public Mono<String> addImage(String id, Mono<FilePart> file, String authorization) {
		return userService.findByEmail(getEmailFromAuthentication(authorization))
				.flatMap(user -> findById(id).filter(artwork -> artwork.getOwnerId().equals(user.getId()))
						.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN,
								"You are not allowed to add an image for another user or artwork")))
						.flatMap(artwork -> {
							List<String> imagesIds = (artwork.getImagesIds() == null) ? new ArrayList<>() : artwork.getImagesIds();
							if (imagesIds.size() < ArtWork.MAX_NUM_IMAGES) {
								return imageService.addPhoto(file).flatMap(image -> {
									imagesIds.add(image.getId());
									artwork.setImagesIds(imagesIds);
									return artWorkRepository.save(artwork)
											.onErrorResume(error -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error by saving image id for artwork")))
											.thenReturn("Image saved for ArtWork.");
								});
							} else {
								return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Maximum number of images reached for the artwork"));
							}
						}))
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Artwork not found")));
	}

	public Mono<Integer> addImages(String id, Flux<FilePart> files, String authorization) {
	    return userService.findByEmail(getEmailFromAuthentication(authorization))
	            .flatMap(user -> findById(id)
	                    .filter(artwork -> artwork.getOwnerId().equals(user.getId()))
	                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to add images for another user or artwork")))
	                    .flatMap(artwork -> {
	                        List<String> imagesIds = (artwork.getImagesIds() == null) ? new ArrayList<>() : artwork.getImagesIds();
	                        int maxAllowedImages = ArtWork.MAX_NUM_IMAGES - imagesIds.size();
	                        return files
	                                .take(maxAllowedImages)
	                                .flatMap(filePart -> imageService.addPhoto(Mono.just(filePart)))
	                                .doOnNext(image -> imagesIds.add(image.getId()))
	                                .then(Mono.fromRunnable(() -> artwork.setImagesIds(imagesIds)))
	                                .then(artWorkRepository.save(artwork))
	                                .onErrorResume(error -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error by saving image ids for artwork")))
	                                .thenReturn(maxAllowedImages);
	                    }))
	            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Artwork not found")));
	}
	
	public Mono<Void> delete(String id, String authorization) {
	    return userService.findByEmail(getEmailFromAuthentication(authorization))
	            .flatMap(user -> findById(id)
	                    .filter(artwork -> artwork.getOwnerId().equals(user.getId()))
	                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to remove an artwork for another user")))
	                    .flatMap(artwork -> {
	                        List<String> imageIds = artwork.getImagesIds();
	                        Mono<Void> deleteImages;
	                        if (imageIds != null) {
	                            deleteImages = imageService.deleteAllByIds(imageIds);
	                        } else {
	                            deleteImages = Mono.empty();
	                        }
	                        return deleteImages.then(artWorkRepository.delete(artwork))
	                        		.then();
	                    }))
	            .then();
	}

	
	public Mono<Void> deleteAllByIds(List<String> ids, String authorization) {
	    return Flux.fromIterable(ids)
	            .flatMap(id -> delete(id, authorization))
	            .then();
	}
	
	private String getEmailFromAuthentication(String authorization) {
		String token = authorization.substring(7);
	    return jwtService.extractUsername(token);
	}

	
}
