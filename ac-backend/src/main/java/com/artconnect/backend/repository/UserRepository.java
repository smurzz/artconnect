package com.artconnect.backend.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.artconnect.backend.model.user.Status;
import com.artconnect.backend.model.user.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String>{
	
	 Mono<User> findByEmail(String email);
	 
	 Flux<User> findByisAccountEnabled(Status isAccountEnabled);
	 
	 Flux<User> findByFirstnameIgnoreCase(String firstname);
	 
	 Flux<User> findByLastnameIgnoreCase(String lastname);
	 
	 Flux<User> findByFirstnameIgnoreCaseAndLastnameIgnoreCase(String firstname, String lastname);

}
