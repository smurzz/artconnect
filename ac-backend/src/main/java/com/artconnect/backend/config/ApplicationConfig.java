package com.artconnect.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.artconnect.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

	private final UserRepository repository;

	@Bean
	ReactiveUserDetailsService userDetailsService() {

		return email -> repository.findByEmail(email)
				.map(user -> User
						.withUsername(user.getEmail())
						.password(user.getPassword())
						.roles(user.getRole().toString())
						.build());
	}

	@Bean
	ReactiveAuthenticationManager reactiveAuthenticationManager() {
		var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService());
		authenticationManager.setPasswordEncoder(passwordEncoder());
		return authenticationManager;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
