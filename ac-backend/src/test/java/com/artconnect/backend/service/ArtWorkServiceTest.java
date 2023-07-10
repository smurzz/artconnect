package com.artconnect.backend.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.artconnect.backend.model.Image;
import com.artconnect.backend.model.gallery.Gallery;
import com.artconnect.backend.model.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.controller.response.ArtWorkResponse;
import com.artconnect.backend.model.artwork.ArtDirection;
import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.model.user.Role;
import com.artconnect.backend.repository.ArtWorkRepository;
import com.artconnect.backend.repository.GalleryRepository;
import com.artconnect.backend.service.ImageService;
import com.artconnect.backend.service.UserService;
import com.artconnect.backend.model.artwork.ArtDirection;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ArtWorkServiceTest {

    @Mock
    private ArtWorkRepository artWorkRepository;

    @Mock
    private GalleryRepository galleryRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private ArtWorkService artWorkService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Mocking the behavior of artWorkRepository.findAll() method
        when(artWorkRepository.findAll()).thenReturn(Flux.just(new ArtWork(), new ArtWork()));

        // Call the method to be tested
        Flux<ArtWork> result = artWorkService.findAll();

        // Verify the result
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void testFindByOwnerId() {
        String ownerId = "owner123";

        // Mocking the behavior of artWorkRepository.findByOwnerId() method
        when(artWorkRepository.findByOwnerId(ownerId)).thenReturn(Flux.just(new ArtWork(), new ArtWork()));

        // Call the method to be tested
        Flux<ArtWork> result = artWorkService.findByOwnerId(ownerId);

        // Verify the result
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void testFindByOwnerName() {
        String ownerName = "John Doe";

        // Mocking the behavior of artWorkRepository.findByOwnerName() method
        when(artWorkRepository.findByOwnerName(ownerName)).thenReturn(Flux.just(new ArtWork(), new ArtWork()));

        // Call the method to be tested
        Flux<ArtWork> result = artWorkService.findByOwnerName(ownerName);

        // Verify the result
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void testFindByGalleryId() {
        String galleryId = "gallery123";

        // Mocking the behavior of artWorkRepository.findByGalleryId() method
        when(artWorkRepository.findByGalleryId(galleryId)).thenReturn(Flux.just(new ArtWork(), new ArtWork()));

        // Call the method to be tested
        Flux<ArtWork> result = artWorkService.findByGalleryId(galleryId);

        // Verify the result
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void testFindByMaterialsIn() {
        List<String> materials = Arrays.asList("canvas", "oil");

        // Mocking the behavior of artWorkRepository.findByMaterialsIn() method
        when(artWorkRepository.findByMaterialsIn(materials)).thenReturn(Flux.just(new ArtWork(), new ArtWork()));

        // Call the method to be tested
        Flux<ArtWork> result = artWorkService.findByMaterialsIn(materials);

        // Verify the result
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void testFindByTagsIn() {
        List<String> tags = Arrays.asList("abstract", "modern");

        // Mocking the behavior of artWorkRepository.findByTagsInIgnoreCase() method
        when(artWorkRepository.findByTagsInIgnoreCase(tags)).thenReturn(Flux.just(new ArtWork(), new ArtWork()));

        // Call the method to be tested
        Flux<ArtWork> result = artWorkService.findByTagsIn(tags);

        // Verify the result
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void testFindByArtDirectionsIn() {
        List<ArtDirection> artDirections = Arrays.asList(
                ArtDirection.ABSTRACT,
                ArtDirection.REALISM,
                ArtDirection.IMPRESSIONISM,
                ArtDirection.SURREALISM,
                ArtDirection.EXPRESSIONISM,
                ArtDirection.MINIMALISM,
                ArtDirection.CUBISM,
                ArtDirection.POP_ART,
                ArtDirection.CONCEPTUAL_ART,
                ArtDirection.STREET_ART_GRAFFITI
        );

        // Mocking the behavior of artWorkRepository.findByArtDirectionsIn() method
        when(artWorkRepository.findByArtDirectionsIn(artDirections)).thenReturn(Flux.just(new ArtWork(), new ArtWork()));

        // Call the method to be tested
        Flux<ArtWork> result = artWorkService.findByArtDirectionsIn(artDirections);

        // Verify the result
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();

        // Verify the argument passed to artWorkRepository.findByArtDirectionsIn()
        verify(artWorkRepository).findByArtDirectionsIn(artDirections);
    }
    @Test
    void testFindByPriceLessThan() {
        double price = 100.0;

        // Mocking the behavior of artWorkRepository.findByPriceLessThan() method
        when(artWorkRepository.findByPriceLessThan(price)).thenReturn(Flux.just(new ArtWork(), new ArtWork()));

        // Call the method to be tested
        Flux<ArtWork> result = artWorkService.findByPriceLessThan(price);

        // Verify the result
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void testFindByPriceBetween() {
        double minPrice = 50.0;
        double maxPrice = 100.0;

        // Mocking the behavior of artWorkRepository.findByPriceBetween() method
        when(artWorkRepository.findByPriceBetween(minPrice, maxPrice)).thenReturn(Flux.just(new ArtWork(), new ArtWork()));

        // Call the method to be tested
        Flux<ArtWork> result = artWorkService.findByPriceBetween(minPrice, maxPrice);

        // Verify the result
        StepVerifier.create(result)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void testFindById() {
        String id = "artwork123";
        ArtWork artwork = new ArtWork();

        // Mocking the behavior of artWorkRepository.findById() method
        when(artWorkRepository.findById(id)).thenReturn(Mono.just(artwork));

        // Call the method to be tested
        Mono<ArtWork> result = artWorkService.findById(id);

        // Verify the result
        StepVerifier.create(result)
                .expectNext(artwork)
                .verifyComplete();
    }

}
