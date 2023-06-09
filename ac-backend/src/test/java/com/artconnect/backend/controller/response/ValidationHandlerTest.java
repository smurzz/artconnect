package com.artconnect.backend.controller.response;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.controller.AuthenticationController;
import com.artconnect.backend.controller.request.RegisterRequest;


import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


//NEED TO BE CORRECTED
public class ValidationHandlerTest {

    @InjectMocks
    private ValidationHandler validationHandler;


    @BeforeEach
    public void setup() {
        validationHandler = new ValidationHandler();
    }

    @Test
    public void handleImageUploadException_ReturnsBadRequestStatus() {
        ConstraintViolationException exception = Mockito.mock(ConstraintViolationException.class);
        Mono<ResponseEntity<String>> response = validationHandler.handleImageUploadException(exception);
        ResponseEntity<String> result = response.block();

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void handleImageUploadException_ReturnsExpectedErrorMessage() {
        ConstraintViolationException exception = Mockito.mock(ConstraintViolationException.class);
        Mono<ResponseEntity<String>> response = validationHandler.handleImageUploadException(exception);
        ResponseEntity<String> result = response.block();

        Assertions.assertEquals("Image is bigger than 5Mb or is not PNG, JPEG, or JPG", result.getBody());
    }

    @Test
    public void handleImageUploadException_ReturnsMonoWithExpectedValue() {
        ConstraintViolationException exception = Mockito.mock(ConstraintViolationException.class);
        Mono<ResponseEntity<String>> response = validationHandler.handleImageUploadException(exception);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response instanceof Mono);
    }

    @Test
    public void handleImageUploadException_ReturnsExpectedResponseEntity() {
        ConstraintViolationException exception = Mockito.mock(ConstraintViolationException.class);
        Mono<ResponseEntity<String>> response = validationHandler.handleImageUploadException(exception);
        ResponseEntity<String> result = response.block();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        Assertions.assertEquals("Image is bigger than 5Mb or is not PNG, JPEG, or JPG", result.getBody());
    }

    @Test
    public void handleImageUploadException_DoesNotThrowException() {
        ConstraintViolationException exception = Mockito.mock(ConstraintViolationException.class);

        Assertions.assertDoesNotThrow(() -> {
            validationHandler.handleImageUploadException(exception).block();
        }, "Expected no exception to be thrown");
    }

    @Test
    public void handleImageUploadException_ReturnsMonoWithValueSet() {
        ConstraintViolationException exception = Mockito.mock(ConstraintViolationException.class);
        Mono<ResponseEntity<String>> response = validationHandler.handleImageUploadException(exception);

        String expectedErrorMessage = "Image is bigger than 5Mb or is not PNG, JPEG, or JPG";
        Mono<String> expectedMono = Mono.just(expectedErrorMessage);

        Assertions.assertEquals(expectedMono.block(), response.map(ResponseEntity::getBody).block());
    }

    @Test
    void handleDuplicateKeyException_emailExists() {
        ValidationHandler validationHandler = new ValidationHandler();
        DuplicateKeyException exception = new DuplicateKeyException("Duplicate key error: email already exists");

        Mono<ResponseEntity<String>> result = validationHandler.handleDuplicateKeyException(exception);

        assertTrue(result instanceof Mono);
        result.subscribe(
                response -> {
                    assertTrue(response instanceof ResponseEntity);
                    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                    assertEquals("Email already exists", response.getBody());
                },
                error -> {
                    throw new AssertionError("Unexpected error: " + error.getMessage());
                }
        );
    }

    @Test
    void handleDuplicateKeyException_generic() {
        ValidationHandler validationHandler = new ValidationHandler();
        DuplicateKeyException exception = new DuplicateKeyException("Duplicate key error: some other key");

        Mono<ResponseEntity<String>> result = validationHandler.handleDuplicateKeyException(exception);

        assertTrue(result instanceof Mono);
        result.subscribe(
                response -> {
                    assertTrue(response instanceof ResponseEntity);
                    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
                    assertEquals("Duplicate key error", response.getBody());
                },
                error -> {
                    throw new AssertionError("Unexpected error: " + error.getMessage());
                }
        );
    }


//	private ValidationHandler validationHandler;
//
//	@BeforeEach
//	public void setup() {
//		validationHandler = new ValidationHandler();
//	}
//
//	 @Test
//	    void handleExceptionWithBindExceptionReturnsBadRequestWithErrors() {
//		 	AuthenticationController authenticationController = mock(AuthenticationController.class);
//		 	String field1 = "firstname";
//			String field2 = "lastname";
//			String field3 = "email";
//			String field4 = "password";
//			String objectName1 = "fieldName1";
//			String defaultMessage = "darf nicht leer sein";
//			List<FieldError> fieldErrors = List.of(
//					new FieldError(objectName1, field1, defaultMessage),
//					new FieldError(objectName1, field2, defaultMessage),
//					new FieldError(objectName1, field3, defaultMessage),
//					new FieldError(objectName1, field4, defaultMessage));
//
//			RegisterRequest request = new RegisterRequest();
//
//			Method method = new Object() {
//			}.getClass().getEnclosingMethod();
//			MethodParameter parameter = mock(MethodParameter.class);
//			lenient().when(parameter.getMethod()).thenReturn(method);
//
//			BindingResult bindingResult = mock(BindingResult.class);
//			when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);
//
//	        // Creating a WebTestClient instance
//			WebTestClient webTestClient = WebTestClient.bindToController(validationHandler, authenticationController).build();
//
//	        // Sending a request and capturing the response
//	        webTestClient.post()
//	                .uri("/auth/register")
//	                .contentType(MediaType.APPLICATION_JSON)
//	                .body(Mono.just(request), RegisterRequest.class)
//	                .exchange()
//	                .expectStatus().isBadRequest()
//	                .expectBodyList(String.class)
//	                .value(responseBody -> {
//	                	List<String> expectedErrors = Arrays.asList(
//	                			"firstname: darf nicht leer sein",
//	                		    "lastname: darf nicht leer sein",
//	                		    "email: darf nicht leer sein",
//	                		    "password: darf nicht leer sein"
//	                		);
//	                	List<String> responseErrors = Arrays.asList(responseBody.get(0).split(","));
//
//	                		assertThat(responseErrors)
//	                		    .hasSize(expectedErrors.size())
//	                		    .containsExactlyInAnyOrderElementsOf(responseErrors);
//	                });
//	    }
//
//
//	    @Test
//	    void handleExceptionWithWebExchangeBindExceptionReturnsBadRequestWithErrors() {
//	    	String field1 = "field1";
//			String field2 = "field2";
//			String objectName1 = "fieldName1";
//			String objectName2 = "fieldName2";
//			String defaultMessage1 = "message1";
//			String defaultMessage2 = "message2";
//			List<FieldError> fieldErrors = List.of(new FieldError(objectName1, field1, defaultMessage1),
//					new FieldError(objectName2, field2, defaultMessage2));
//
//			Method method = new Object() {
//			}.getClass().getEnclosingMethod();
//			MethodParameter parameter = mock(MethodParameter.class);
//			lenient().when(parameter.getMethod()).thenReturn(method);
//
//			BindingResult bindingResult = mock(BindingResult.class);
//			when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);
//
//			WebExchangeBindException exception = new WebExchangeBindException(parameter, bindingResult);
//
//			Mono<String> response= validationHandler.handleException(exception);
//
//			StepVerifier.create(response)
//	        .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException
//	                && ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.BAD_REQUEST
//	                && ((ResponseStatusException) throwable).getReason().equals("fieldName1.field1: message1, fieldName2.field2: message2"))
//	        .verify();
//	    }
//
//	    @Test
//	    void handleDuplicateKeyExceptionEmailExistsReturnsBadRequestWithEmailExistsMessage() {
//	        DuplicateKeyException exception = new DuplicateKeyException("Duplicate entry 'test@test.com' for key 'email'");
//	        Mono<ResponseEntity<String>> response = validationHandler.handleDuplicateKeyException(exception);
//
//	        StepVerifier.create(response)
//	                .expectNextMatches(entity -> {
//	                    HttpStatusCode status = entity.getStatusCode();
//	                    String body = entity.getBody();
//	                    return status == HttpStatus.BAD_REQUEST && body.equals("Email already exists");
//	                })
//	                .expectComplete()
//	                .verify();
//	    }
//
//	    @Test
//	    void handleDuplicateKeyExceptionOtherKeyReturnsBadRequestWithDefaultMessage() {
//	        DuplicateKeyException exception = new DuplicateKeyException("Duplicate entry '123' for key 'primary_key'");
//	        Mono<ResponseEntity<String>> response = validationHandler.handleDuplicateKeyException(exception);
//
//	        StepVerifier.create(response)
//	                .expectNextMatches(entity -> {
//	                	HttpStatusCode status = entity.getStatusCode();
//	                    String body = entity.getBody();
//	                    return status == HttpStatus.BAD_REQUEST && body.equals("Duplicate key error");
//	                })
//	                .expectComplete()
//	                .verify();
//	    }

}
