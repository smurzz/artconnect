package com.artconnect.backend.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.artconnect.backend.controller.request.ResetPasswordRequest;
import com.artconnect.backend.service.ForgotPasswordService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Validated
public class ForgotPasswordController {
	
	private final ForgotPasswordService forgotPasswordService;
	
	@GetMapping("/forgot-password")
    public Mono<String> forgotPassword(@RequestParam("email") String email) {
        return forgotPasswordService.processForgotPassword(email);
	}
	
	@PostMapping("/reset-password")
    public Mono<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request){
        return forgotPasswordService.updatePassword(request);
    }

}
