package com.artconnect.backend.controller.response;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import reactor.core.publisher.Mono;

@ControllerAdvice
public class ValidationHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<List<String>>> handleException(WebExchangeBindException e) {
        var errors = e.getBindingResult()
        		.getFieldErrors()
                .stream()
                .map(fieldError -> new String( fieldError.getField() + ": " + fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return Mono.just(ResponseEntity.badRequest().body(errors));
    }
    
	@ExceptionHandler(DuplicateKeyException.class)
    public Mono<ResponseEntity<String>> handleDuplicateKeyException(DuplicateKeyException e) {
        String message = e.getMessage();
        if (message.contains("email")) {
            return Mono.just(ResponseEntity.badRequest().body("Email already exists"));
        } else {
            return Mono.just(ResponseEntity.badRequest().body("Duplicate key error"));
        }
	}

}
