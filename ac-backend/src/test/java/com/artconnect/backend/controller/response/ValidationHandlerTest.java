package com.artconnect.backend.controller.response;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.core.codec.DecodingException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import reactor.core.publisher.Mono;


import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ValidationHandlerTest {

    @InjectMocks
    private ValidationHandler validationHandler;


    @BeforeEach
    public void setup() {
        validationHandler = new ValidationHandler();
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

    @Test
    void handleDecodingException_shouldReturnBadRequestStatusWithDefaultErrorMessage() {
        DecodingException exception = mock(DecodingException.class);

        ValidationHandler validationHandler = new ValidationHandler();
        Mono<ResponseEntity<String>> result = validationHandler.handleDecodingException(exception);

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, result::block);
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
        assertEquals("One of enum value in the list is not valid", responseStatusException.getReason());
    }

    @Test
    void handleDecodingException_shouldReturnBadRequestStatusWithEnumErrorMessage() {
        DecodingException exception = new DecodingException("One of enum value in the list is not valid");

        ValidationHandler validationHandler = new ValidationHandler();
        Mono<ResponseEntity<String>> result = validationHandler.handleDecodingException(exception);

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, result::block);
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
        assertEquals("One of enum value in the list is not valid", responseStatusException.getReason());
    }

    @Test
    void handleDecodingException_shouldReturnBadRequestStatusWithCustomErrorMessage() {
        DecodingException exception = new DecodingException("Custom error message");

        ValidationHandler validationHandler = new ValidationHandler();
        Mono<ResponseEntity<String>> result = validationHandler.handleDecodingException(exception);

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, result::block);
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
        assertEquals("One of enum value in the list is not valid", responseStatusException.getReason());
    }

    @Test
    void handleDuplicateKeyException_shouldReturnBadRequestStatusWithEmailErrorMessage() {
        DuplicateKeyException exception = new DuplicateKeyException("Duplicate key error: email");

        ValidationHandler validationHandler = new ValidationHandler();
        Mono<ResponseEntity<String>> result = validationHandler.handleDuplicateKeyException(exception);

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, result::block);
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
        assertEquals("Email already exists", responseStatusException.getReason());
    }

    @Test
    void handleDuplicateKeyException_shouldReturnBadRequestStatusWithGalleryErrorMessage() {
        DuplicateKeyException exception = new DuplicateKeyException("Duplicate key error: ownerId");

        ValidationHandler validationHandler = new ValidationHandler();
        Mono<ResponseEntity<String>> result = validationHandler.handleDuplicateKeyException(exception);

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, result::block);
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
        assertEquals("Gallery already exists for the user", responseStatusException.getReason());
    }

    @Test
    void handleDuplicateKeyException_shouldReturnBadRequestStatusWithDefaultErrorMessage() {
        DuplicateKeyException exception = new DuplicateKeyException("Some other duplicate key error");

        ValidationHandler validationHandler = new ValidationHandler();
        Mono<ResponseEntity<String>> result = validationHandler.handleDuplicateKeyException(exception);

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, result::block);
        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
        assertEquals("Duplicate key error", responseStatusException.getReason());
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
