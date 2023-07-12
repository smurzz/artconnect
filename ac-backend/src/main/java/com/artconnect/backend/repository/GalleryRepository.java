package com.artconnect.backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.artconnect.backend.model.gallery.Gallery;
import com.artconnect.backend.model.gallery.GalleryCategory;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GalleryRepository extends ReactiveMongoRepository<Gallery, String> {
	
	Mono<Gallery> findByOwnerId(String ownerId);
	
	Flux<Gallery> findByCategoriesIn(List<GalleryCategory> categories);

}
