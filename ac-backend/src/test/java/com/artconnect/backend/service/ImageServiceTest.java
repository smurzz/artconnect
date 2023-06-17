package com.artconnect.backend.service;

import static org.mockito.Mockito.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import com.artconnect.backend.model.Image;
import com.artconnect.backend.repository.ImageRepository;


public class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    private ImageService imageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        imageService = new ImageService(imageRepository);
    }

    @Test
    public void testGetPhoto_ExistingId_ReturnsImage() {
        String id = "existingId";
        Image image = new Image();
        when(imageRepository.findById(id)).thenReturn(Mono.just(image));

        Mono<Image> result = imageService.getPhoto(id);

        StepVerifier.create(result)
                .expectNext(image)
                .verifyComplete();
    }

    @Test
    public void testGetPhoto_NonExistingId_ReturnsError() {
        String id = "nonExistingId";
        when(imageRepository.findById(id)).thenReturn(Mono.empty());

        Mono<Image> result = imageService.getPhoto(id);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof ResponseStatusException
                                && ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.NOT_FOUND)
                .verify();
    }

    @Test
    public void testGetPhoto_ValidId_ReturnsImage() {
        String id = "validId";
        Image image = new Image();
        when(imageRepository.findById(id)).thenReturn(Mono.just(image));

        Mono<Image> result = imageService.getPhoto(id);

        StepVerifier.create(result)
                .expectNext(image)
                .verifyComplete();

        verify(imageRepository).findById(id);
    }

    @Test
    public void testGetPhoto_NullId_ThrowsException() {
        String id = null;
        when(imageRepository.findById(id)).thenReturn(Mono.empty());

        Mono<Image> result = imageService.getPhoto(id);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof ResponseStatusException
                                && ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.NOT_FOUND)
                .verify();
    }
}