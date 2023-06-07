package com.artconnect.backend.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.artconnect.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserCleanupTask {
	
	@Value("${application.security.jwt.confirm-token.validityInMs}")
	private long confirmTokenValidity;
	
    private final UserRepository userRepository;
	
    @Scheduled(fixedRate = 60000) // 1 minute
	public void cleanupUsers() {
    	userRepository.findByisEnabled(false)
    		.filter(user -> (user.getCreatedAt().getTime() + confirmTokenValidity) <= new Date().getTime())
    		.flatMap(userRepository::delete)
    		.subscribe(); 		
	}

}
