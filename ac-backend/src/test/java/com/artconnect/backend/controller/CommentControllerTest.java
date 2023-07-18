package com.artconnect.backend.controller;


import com.artconnect.backend.controller.request.CommentRequest;
import com.artconnect.backend.controller.request.CommentUpdateRequest;
import com.artconnect.backend.controller.response.ArtWorkResponse;
import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.service.ArtWorkService;
import com.artconnect.backend.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class CommentControllerTest {
    @Mock
    private CommentService commentService;

    @Mock
    private ArtWorkService artWorkService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createComment_ValidRequest_ReturnsArtWorkResponse() {
        // Mock the necessary dependencies
        CommentRequest commentRequest = new CommentRequest();
        ArtWork artWork = new ArtWork();
        when(commentService.create(anyString(), any(CommentRequest.class))).thenReturn(Mono.just(artWork));
        when(artWorkService.mapArtWorkToResponse(any(ArtWork.class))).thenReturn(Mono.just(new ArtWorkResponse()));

        // Invoke the controller method
        Mono<ArtWorkResponse> result = commentController.createComment("artworkId", commentRequest);

        // Verify the result
        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void updateComment_ValidRequest_ReturnsArtWorkResponse() {
        // Mock the necessary dependencies
        CommentUpdateRequest commentRequest = new CommentUpdateRequest();
        ArtWork artWork = new ArtWork();
        when(commentService.update(anyString(), anyString(), any(CommentUpdateRequest.class))).thenReturn(Mono.just(artWork));
        when(artWorkService.mapArtWorkToResponse(any(ArtWork.class))).thenReturn(Mono.just(new ArtWorkResponse()));

        // Invoke the controller method
        Mono<ArtWorkResponse> result = commentController.updateComment("artworkId", "commentId", commentRequest);

        // Verify the result
        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void deleteComment_ValidRequest_ReturnsNoContent() {
        // Mock the necessary dependencies
        when(commentService.delete(anyString(), anyString())).thenReturn(Mono.empty());

        // Invoke the controller method
        Mono<Void> result = commentController.deleteComment("artworkId", "commentId");

        // Verify the result
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }
}