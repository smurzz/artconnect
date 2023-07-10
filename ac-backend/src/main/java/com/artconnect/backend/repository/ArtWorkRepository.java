package com.artconnect.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.artconnect.backend.model.artwork.ArtWork;

import reactor.core.publisher.Flux;

public interface ArtWorkRepository extends ReactiveMongoRepository<ArtWork, String>{
	
	Flux<ArtWork> findByOwnerId(String ownerId);
	
	Flux<ArtWork> findByGalleryId(String galleryId);
	
	Flux<ArtWork> findByOwnerName(String ownerName);
	
	Flux<ArtWork> findByMaterialsIn(List<String> materials);
	
	Flux<ArtWork> findByPriceLessThan(double price);

	Flux<ArtWork> findByPriceBetween(double minPrice, double maxPrice);

}
