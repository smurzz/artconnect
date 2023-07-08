package com.artconnect.backend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.controller.response.ArtWorkResponse;
import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.model.gallery.Gallery;
import com.artconnect.backend.model.user.Role;
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
	                                            List<String> artworkIds = (gallery.getArtworkIds() == null) ? new ArrayList<>() : gallery.getArtworkIds();
	                                            artworkIds.add(savedArtwork.getId());
	                                            gallery.setArtworkIds(artworkIds);
	                                            galleryRepository.save(gallery).subscribe();
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
	                        return imageService.validedAllPhoto(files)
	                                .flatMap(validated -> {
	                                    if (validated) {
	                                        return files
	                                                .take(maxAllowedImages)
	                                                .flatMap(filePart -> imageService.addPhoto(Mono.just(filePart)))
	                                                .doOnNext(image -> imagesIds.add(image.getId()))
	                                                .then(Mono.fromRunnable(() -> artwork.setImagesIds(imagesIds)))
	                                                .then(artWorkRepository.save(artwork))
	                                                .onErrorResume(error -> Mono.error(error))
	                                                .thenReturn(maxAllowedImages);
	                                    } else {
	                                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid image(s)"));
	                                    }
	                                });
	                    }))
	            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Artwork not found")));
	}
	
	public Mono<ArtWork> update(String id, ArtWork artwork, String authorization) {
		return userService.findByEmail(getEmailFromAuthentication(authorization))
				.flatMap(user -> {
					return findById(id)
							.filter(existingArtwork -> existingArtwork.getOwnerId().equals(user.getId()) || user.getRole() == Role.ADMIN)
							.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update an artwork for another user")))
							.flatMap(existingArtwork -> {
								if(user.getRole() == Role.ADMIN) {
									Optional.ofNullable(artwork.getId()).ifPresent(existingArtwork::setId);
									Optional.ofNullable(artwork.getOwnerId()).ifPresent(existingArtwork::setOwnerId);
									Optional.ofNullable(artwork.getGalleryId()).ifPresent(existingArtwork::setOwnerId);
									Optional.ofNullable(artwork.getGalleryTitle()).ifPresent(existingArtwork::setGalleryTitle);
									Optional.ofNullable(artwork.getOwnerId()).ifPresent(existingArtwork::setOwnerId);
								}
								Optional.ofNullable(artwork.getTitle()).ifPresent(existingArtwork::setTitle);
								Optional.ofNullable(artwork.getDescription()).ifPresent(existingArtwork::setDescription);
								Optional.ofNullable(artwork.getYearOfCreation()).ifPresent(existingArtwork::setYearOfCreation);
								Optional.ofNullable(artwork.getDimension()).ifPresent(existingArtwork::setDimension);
								Optional.ofNullable(artwork.getPrice()).ifPresent(existingArtwork::setPrice);
								Optional.ofNullable(artwork.getLocation()).ifPresent(existingArtwork::setLocation);
								Optional.ofNullable(artwork.getMaterials()).ifPresent(existingArtwork::setMaterials);
								Optional.ofNullable(artwork.getArtDirections()).ifPresent(existingArtwork::setArtDirections);
								Optional.ofNullable(artwork.getTags()).ifPresent(existingArtwork::setTags);
								
								return artWorkRepository.save(existingArtwork);
							});
		});
	}

	

	public Mono<ArtWork> addRemoveLike(String id, String authorization) {
		return userService.findByEmail(getEmailFromAuthentication(authorization))
				.flatMap(user -> {
					return findById(id)
							.flatMap(existingArtwork -> {
								existingArtwork.setLike(user.getEmail());
								return artWorkRepository.save(existingArtwork);
							});
		});
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
	                        		.then(galleryRepository.findByOwnerId(user.getId()))
	                                .flatMap(gallery -> {
	                                    gallery.getArtworkIds().remove(id);
	                                    return galleryRepository.save(gallery);
	                                });
	                    }))
	            .then();
	}

	
	public Mono<Void> deleteAllByIds(List<String> ids, String authorization) {
	    return Flux.fromIterable(ids)
	            .flatMap(id -> delete(id, authorization))
	            .then();
	}
	
	public Mono<Void> deleteImageById(String artworkId, String imageId, String authorization){
		return userService.findByEmail(getEmailFromAuthentication(authorization))
				.flatMap(user -> findById(artworkId)
						.filter(artwork -> artwork.getOwnerId().equals(user.getId()))
						.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to remove an image for another user or artwork")))
						.flatMap(artwork -> {
							if(artwork.getImagesIds().contains(imageId)) {
								return imageService.getPhoto(imageId)
	                                    .flatMap(image -> imageService.deleteById(image.getId())
	                                            .then(Mono.fromCallable(() -> {
	                                                List<String> newImageIds = artwork.getImagesIds().stream()
	                                                        .filter(id -> !id.equals(imageId))
	                                                        .toList();
	                                                artwork.setImagesIds(newImageIds);
	                                                return artwork;
	                                            }))
	                                            .flatMap(artWorkRepository::save)
	                                    );
							} else {
								return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Image is not found by ArtWork"));
							}
						}))
				.then();
	}
	
	private String getEmailFromAuthentication(String authorization) {
		String token = authorization.substring(7);
	    return jwtService.extractUsername(token);
	}
	
}
