package com.artconnect.backend.controller.response;

import java.util.stream.Collectors;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ConstraintViolationException;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ValidationHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<String> handleException(WebExchangeBindException e) {
        var errors = e.getBindingResult()
        		.getFieldErrors()
                .stream()
                .map(fieldError -> new String( fieldError.getField() + ": " + fieldError.getDefaultMessage()))
                .collect(Collectors.joining(", "));
        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, errors));
    }
    
	@ExceptionHandler(DuplicateKeyException.class)
    public Mono<ResponseEntity<String>> handleDuplicateKeyException(DuplicateKeyException e) {
        String message = e.getMessage();
        if (message.contains("email")) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists"));
        } if (message.contains("ownerId")) {
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Gallery already exists for the user"));
        } else {
        	return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate key error"));
        }
	}
}
