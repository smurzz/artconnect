package com.artconnect.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.artconnect.backend.controller.request.ResetPasswordRequest;
import com.artconnect.backend.service.ForgotPasswordService;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ForgotPasswordControllerTest {

    @Mock
    private ForgotPasswordService forgotPasswordService;

    @InjectMocks
    private ForgotPasswordController forgotPasswordController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(forgotPasswordController).build();
    }

    @Test
    void testForgotPassword() {
        String email = "test@example.com";
        when(forgotPasswordService.processForgotPassword(email)).thenReturn(Mono.just("success"));

        Mono<String> result = forgotPasswordController.forgotPassword(email);

        verify(forgotPasswordService, times(1)).processForgotPassword(email);
        assertEquals("success", result.block());
    }

    @Test
    void testResetPassword() {
        ResetPasswordRequest request = new ResetPasswordRequest("test@example.com", "newPassword");
        when(forgotPasswordService.updatePassword(request)).thenReturn(Mono.just("success"));

        webTestClient.post()
                .uri("/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(result -> assertEquals("success", result));

        verify(forgotPasswordService, times(1)).updatePassword(request);
    }

}



