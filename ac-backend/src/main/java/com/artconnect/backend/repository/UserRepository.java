package com.artconnect.backend.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.artconnect.backend.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String>{
	
	 Mono<User> findByEmail(String email);
	 
	 Flux<User> findByisEnabled(boolean isEnabled);

}
