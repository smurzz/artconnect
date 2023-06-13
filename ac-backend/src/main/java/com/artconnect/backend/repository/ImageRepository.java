package com.artconnect.backend.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.artconnect.backend.model.Image;

public interface ImageRepository extends ReactiveMongoRepository<Image, String>{

}
