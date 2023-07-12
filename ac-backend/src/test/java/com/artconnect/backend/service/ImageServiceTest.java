package com.artconnect.backend.service;


import com.artconnect.backend.model.Image;
import com.artconnect.backend.repository.ImageRepository;
import com.artconnect.backend.validation.ImageValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.awt.image.DataBuffer;
import java.util.Arrays;
import java.util.List;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;




class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;



    @Mock
    private ImageValidation imageValidation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        imageService = new ImageService(imageRepository);
    }



    @Test
    void getPhoto_ValidId_ReturnsMonoImage() {
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
    void getPhoto_InvalidId_ReturnsMonoError() {
        String id = "invalidId";
        when(imageRepository.findById(id)).thenReturn(Mono.empty());

        Mono<Image> result = imageService.getPhoto(id);

        StepVerifier.create(result)
                .expectError(ResponseStatusException.class)
                .verify();
        verify(imageRepository).findById(id);
    }

    @Test
    void getPhotosByIds_ValidIds_ReturnsFluxImage() {
        List<String> imageIds = Arrays.asList("id1", "id2");
        Image image1 = new Image();
        Image image2 = new Image();
        when(imageRepository.findAllById(imageIds)).thenReturn(Flux.just(image1, image2));

        Flux<Image> result = imageService.getPhotosByIds(imageIds);

        StepVerifier.create(result)
                .expectNext(image1, image2)
                .verifyComplete();
        verify(imageRepository).findAllById(imageIds);
    }

    @Test
    void deleteById_ValidId_ReturnsMonoVoid() {
        String id = "validId";
        when(imageRepository.deleteById(id)).thenReturn(Mono.empty());

        Mono<Void> result = imageService.deleteById(id);

        StepVerifier.create(result)
                .verifyComplete();
        verify(imageRepository).deleteById(id);
    }

    @Test
    void deleteAllByIds_ValidIds_ReturnsMonoVoid() {
        List<String> ids = Arrays.asList("id1", "id2");
        when(imageRepository.deleteAllById(ids)).thenReturn(Mono.empty());

        Mono<Void> result = imageService.deleteAllByIds(ids);

        StepVerifier.create(result)
                .verifyComplete();
        verify(imageRepository).deleteAllById(ids);
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