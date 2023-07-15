package com.artconnect.backend.service;

import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.model.gallery.Gallery;
import com.artconnect.backend.model.user.User;
import com.artconnect.backend.repository.GalleryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

class RatingServiceTest {

    @Mock
    private GalleryRepository galleryRepository;

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private GalleryService galleryService;

    @InjectMocks
    private RatingService ratingService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addRating_ShouldSetEvaluationAndSaveGallery() {
        // Mock input data
        String id = "galleryId";
        Integer value = 5;
        String authorization = "Bearer token";

        // Mock userService.findByEmail
        User user = new User();
        user.setId("userId");
        when(userService.findByEmail(anyString())).thenReturn(Mono.just(user));

        // Mock galleryService.findById
        Gallery existingGallery = new Gallery();
        when(galleryService.findById(eq(id))).thenReturn(Mono.just(existingGallery));

        // Mock jwtService.extractUsername
        String email = "user@example.com";
        when(jwtService.extractUsername(eq("token"))).thenReturn(email);

        // Mock galleryRepository.save
        when(galleryRepository.save(eq(existingGallery))).thenReturn(Mono.just(existingGallery));

        // Perform the test
        Mono<Gallery> result = ratingService.addRating(id, value, authorization);

        // Verify the interactions and assertions
        StepVerifier.create(result)
                .expectNext(existingGallery)
                .verifyComplete();

        verify(userService).findByEmail(eq(email));
        verify(galleryService).findById(eq(id));
        verify(galleryRepository).save(eq(existingGallery));
        verify(jwtService).extractUsername(eq("token"));
    }
}