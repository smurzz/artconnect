package com.artconnect.backend.controller;

import com.artconnect.backend.controller.request.ArtWorkRequest;
import com.artconnect.backend.controller.response.ArtWorkResponse;
import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.service.ArtWorkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import com.artconnect.backend.controller.request.ArtWorkUpdateRequest;

class ArtWorkControllerTest {

    @Mock
    private ArtWorkService artWorkService;

    @InjectMocks
    private ArtWorkController artWorkController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllArtWorks_shouldReturnAllArtWorks() {
        ArtWork artWork = createArtWork();
        when(artWorkService.findAll()).thenReturn(Flux.just(artWork));
        Flux<ArtWorkResponse> response = artWorkController.getAllArtWorks();
    }

    @Test
    void getArtWorkById_shouldReturnArtWorkById() {
        String id = "artworkId";
        ArtWork artWork = createArtWork();
        when(artWorkService.findById(id)).thenReturn(Mono.just(artWork));
        Mono<ArtWorkResponse> response = artWorkController.getArtWorkById(id);
        // Assertions and verifications
    }

    @Test
    void createArtWork_shouldCreateNewArtWork() {
        String authorization = "Bearer token";
        ArtWorkRequest artWorkRequest = createArtWorkRequest();
        ArtWork artWork = createArtWork();
        when(artWorkService.create(any(ArtWork.class), eq(authorization))).thenReturn(Mono.just(artWork));
        Mono<ArtWorkResponse> response = artWorkController.createArtWork(authorization, artWorkRequest);
    }


    @Test
    void updateMyArtWork_shouldUpdateArtWork() {
        String id = "artworkId";
        String authorization = "Bearer token";
        ArtWorkUpdateRequest artworkRequest = createArtWorkUpdateRequest();
        ArtWork artWork = createArtWork();
        when(artWorkService.update(eq(id), any(ArtWork.class), eq(authorization))).thenReturn(Mono.just(artWork));
        Mono<ArtWorkResponse> response = artWorkController.updateMyArtWork(id, artworkRequest, authorization);
        // Assertions and verifications
    }

    @Test
    void addRemoveLikeByArtWork_shouldAddOrRemoveLike() {
        String id = "artworkId";
        String authorization = "Bearer token";
        ArtWork artWork = createArtWork();
        when(artWorkService.addRemoveLike(eq(id), eq(authorization))).thenReturn(Mono.just(artWork));
        Mono<ArtWorkResponse> response = artWorkController.addRemoveLikeByArtWork(id, authorization);
        // Assertions and verifications
    }

    @Test
    void deleteImageInArtWorkById_shouldDeleteImageInArtWork() {
        String id = "artworkId";
        String imageId = "imageId";
        String authorization = "Bearer token";
        when(artWorkService.deleteImageById(eq(id), eq(imageId), eq(authorization))).thenReturn(Mono.empty());
        Mono<Void> response = artWorkController.deleteImageInArtWorkById(id, imageId, authorization);
    }

    @Test
    void deleteArtWork_shouldDeleteArtWork() {
        String id = "artworkId";
        String authorization = "Bearer token";
        when(artWorkService.delete(eq(id), eq(authorization))).thenReturn(Mono.empty());
        Mono<Void> response = artWorkController.deleteArtWork(id, authorization);
        // Assertions and verifications
    }

    private ArtWork createArtWork() {
        ArtWork artWork = new ArtWork();
        // Set necessary properties for the artWork
        return artWork;
    }

    private ArtWorkRequest createArtWorkRequest() {
        ArtWorkRequest artWorkRequest = new ArtWorkRequest();
        // Set necessary properties for the artWorkRequest
        return artWorkRequest;
    }

    private FilePart createFilePart() {
        // Create and return a mock FilePart object
        return null;
    }

    private Flux<FilePart> createFileParts() {
        // Create and return a mock Flux<FilePart> object
        return null;
    }

    private ArtWorkUpdateRequest createArtWorkUpdateRequest() {
        ArtWorkUpdateRequest artworkUpdateRequest = new ArtWorkUpdateRequest();
        // Set necessary properties for the artworkUpdateRequest
        return artworkUpdateRequest;
    }

}
