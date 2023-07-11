package com.artconnect.backend.controller;

import com.artconnect.backend.controller.request.AuthenticationRequest;
import com.artconnect.backend.controller.request.RegisterRequest;
import com.artconnect.backend.controller.response.AuthenticationResponse;
import com.artconnect.backend.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.artconnect.backend.controller.request.ResetPasswordRequest;
import com.artconnect.backend.service.ForgotPasswordService;
import reactor.core.publisher.Mono;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.when;

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

    @WebFluxTest(controllers = AuthenticationController.class)
    public static class AuthenticationControllerTest {

        @Autowired
        private WebTestClient webClient;

        @MockBean
        private AuthenticationService service;


        @InjectMocks
        private AuthenticationController controller;

        @Mock
        private AuthenticationService service2;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testRegisterUser() {
            RegisterRequest request = new RegisterRequest();
            // set request fields as needed

            when(service.register(request)).thenReturn(Mono.just("Success"));

            Mono<String> result = controller.registerUser(request);

            assertEquals("Success", result.block());
            //verify(service, times(1)).register(request);
        }




        @Test
        public void testGetHelloWorldFromAuth() {
            webClient.get()
                    .uri("/auth/")
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody(String.class)
                    .value(response -> assertEquals("Hello from ArtConnect Security!", response));
        }


        @Test
        public void testLogin() {
            AuthenticationRequest request = new AuthenticationRequest();
            // set request fields as needed

            AuthenticationResponse response = new AuthenticationResponse();
            // set response fields as needed

            when(service.login(request)).thenReturn(Mono.just(response));

            Mono<AuthenticationResponse> result = controller.login(request);

            assertEquals(response, result.block());
            verify(service, times(1)).login(request);
        }



    }
}



