package com.artconnect.backend.controller;

import com.artconnect.backend.controller.RatingController;
import com.artconnect.backend.controller.response.GalleryResponse;
import com.artconnect.backend.model.gallery.Gallery;
import com.artconnect.backend.service.GalleryService;
import com.artconnect.backend.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@Validated
class RatingControllerTest {

    @Mock
    private GalleryService galleryService;

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddRatingToGallery() {
        // Mock data
        String galleryId = "galleryId";
        Integer ratingValue = 4;
        String authorization = "Bearer token";
        Gallery gallery = new Gallery(); // Create a test gallery object

        // Mock the galleryService and ratingService behavior
        when(ratingService.addRating(anyString(), anyInt(), anyString())).thenReturn(Mono.just(gallery));
        when(galleryService.mapGalleryToResponse(any())).thenReturn(Mono.just(new GalleryResponse()));

        // Call the controller method
        Mono<GalleryResponse> result = ratingController.addRatingToGallery(galleryId, ratingValue, authorization);

        // Verify the behavior using StepVerifier
        StepVerifier.create(result)
                .expectNextMatches(response -> response != null && response instanceof GalleryResponse)
                .verifyComplete();
    }
}