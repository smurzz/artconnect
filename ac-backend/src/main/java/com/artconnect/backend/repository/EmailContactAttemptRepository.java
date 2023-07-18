package com.artconnect.backend.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.artconnect.backend.model.EmailContactAttempt;

import reactor.core.publisher.Flux;

public interface EmailContactAttemptRepository extends ReactiveMongoRepository<EmailContactAttempt, String> {
	
	Flux<EmailContactAttempt> findBySenderId(String senderId);
	
	Flux<EmailContactAttempt> findByArtistId(String artistId);

}
