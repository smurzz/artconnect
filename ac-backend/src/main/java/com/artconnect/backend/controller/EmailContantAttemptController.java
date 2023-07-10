package com.artconnect.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.artconnect.backend.controller.request.EmailContactAttemptRequest;
import com.artconnect.backend.service.EmailContantAttemptService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/artworks/{id}/contact")
@RequiredArgsConstructor
@Validated
public class EmailContantAttemptController {
	
	private final EmailContantAttemptService emailContantAttemptService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<String> createEmailContactRequest(
			@PathVariable("id") String id,
			@Valid @RequestBody EmailContactAttemptRequest emailContactRequest) {
		return emailContantAttemptService.create(id, emailContactRequest);
	}

}
