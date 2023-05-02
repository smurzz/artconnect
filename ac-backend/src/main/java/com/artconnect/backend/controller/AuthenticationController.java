package com.artconnect.backend.controller;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.artconnect.backend.controller.request.AuthenticationRequest;
import com.artconnect.backend.controller.request.RegisterRequest;
import com.artconnect.backend.controller.response.AuthenticationResponse;
import com.artconnect.backend.service.AuthenticationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {
	
	private final AuthenticationService service;

	@CrossOrigin(origins = "http://localhost:3001")
	@PostMapping("/register")
	public Mono<String> registerUser(@Valid @RequestBody RegisterRequest request){
		return service.register(request);
	}
	
	@PostMapping("/login")
	public Mono<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request) {
		return service.login(request);
	}
	
	@PostMapping("/refresh")
	public Mono<AuthenticationResponse> refreshToken(ServerHttpRequest request, ServerHttpResponse response) {
	    return service.refreshToken(request, response);
	}
	
	@GetMapping("/confirm-account")
    public Mono<String> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        return service.confirmEmail(confirmationToken);
    }

}
