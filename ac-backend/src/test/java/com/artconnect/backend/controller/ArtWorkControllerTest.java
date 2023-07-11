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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.artconnect.backend.controller.request.ArtWorkUpdateRequest;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;


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
    }

    @Test
    void addRemoveLikeByArtWork_shouldAddOrRemoveLike() {
        String id = "artworkId";
        String authorization = "Bearer token";
        ArtWork artWork = createArtWork();
        when(artWorkService.addRemoveLike(eq(id), eq(authorization))).thenReturn(Mono.just(artWork));
        Mono<ArtWorkResponse> response = artWorkController.addRemoveLikeByArtWork(id, authorization);
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
    }

    @Test
    void getArtworksByParam_withOwnerId_shouldReturnArtWorksByOwnerId() {
        String ownerId = "ownerId";
        ArtWork artWork = createArtWork();
        ArtWorkResponse artWorkResponse = createArtWorkResponse(artWork);
        when(artWorkService.findByOwnerId(ownerId)).thenReturn(Flux.just(artWork));
        when(artWorkService.mapArtWorkToResponse(artWork)).thenReturn(Mono.just(artWorkResponse));

        Flux<ArtWorkResponse> response = artWorkController.getArtworksByParam(ownerId, null, null, null, null, null, null, null, null);
        StepVerifier.create(response)
                .expectNext(artWorkResponse)
                .verifyComplete();
    }

    @Test
    void addPhotoToArtWork_shouldAddPhotoToArtWork() {
        String id = "artworkId";
        String authorization = "Bearer token";
        FilePart filePart = createFilePart();
        String expectedResponse = "Image added successfully";
        when(artWorkService.addImage(eq(id), any(Mono.class), eq(authorization))).thenReturn(Mono.just(expectedResponse));

        Mono<String> response = artWorkController.addPhotoToArtWork(id, Mono.just(filePart), authorization);
        StepVerifier.create(response)
                .expectNext(expectedResponse)
                .verifyComplete();
    }

    @Test
    void addPhotosToArtWork_shouldAddPhotosToArtWork() {
        String id = "artworkId";
        String authorization = "Bearer token";
        Flux<FilePart> fileParts = createFileParts();
        int expectedImageNum = 3;
        when(artWorkService.addImages(eq(id), any(Flux.class), eq(authorization)))
                .thenReturn(Mono.just(expectedImageNum));

        Mono<String> response = artWorkController.addPhotosToArtWork(id, fileParts, authorization);
        StepVerifier.create(response)
                .expectNext("Images saved for ArtWork.")
                .verifyComplete();
    }

    @Test
    void getArtWorkById_shouldReturnArtWorkResponse() {
        String artWorkId = "artWorkId";
        ArtWork artWork = createArtWork();
        ArtWorkResponse artWorkResponse = createArtWorkResponseLeerAttr();
        when(artWorkService.findById(artWorkId)).thenReturn(Mono.just(artWork));
        when(artWorkService.mapArtWorkToResponse(artWork)).thenReturn(Mono.just(artWorkResponse));

        Mono<ArtWorkResponse> response = artWorkController.getArtWorkById(artWorkId);
        StepVerifier.create(response)
                .expectNext(artWorkResponse)
                .verifyComplete();
    }

    @Test
    void getArtworksByParam_withOwnerId_shouldReturnArtWorks() {
        String ownerId = "ownerId";
        List<ArtWork> artWorks = Arrays.asList(createArtWork(), createArtWork());
        when(artWorkService.findByOwnerId(ownerId)).thenReturn(Flux.fromIterable(artWorks));
        when(artWorkService.mapArtWorkToResponse(any(ArtWork.class))).thenReturn(Mono.just(createArtWorkResponseLeerAttr()));

        Flux<ArtWorkResponse> response = artWorkController.getArtworksByParam(ownerId, null, null, null, null, null, null, null, null);
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void getArtworksByParam_withGalleryId_shouldReturnArtWorks() {
        String galleryId = "galleryId";
        List<ArtWork> artWorks = Arrays.asList(createArtWork(), createArtWork());
        when(artWorkService.findByGalleryId(galleryId)).thenReturn(Flux.fromIterable(artWorks));
        when(artWorkService.mapArtWorkToResponse(any(ArtWork.class))).thenReturn(Mono.just(createArtWorkResponseLeerAttr()));

        Flux<ArtWorkResponse> response = artWorkController.getArtworksByParam(null, galleryId, null, null, null, null, null, null, null);
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void getArtworksByParam_withOwnerName_shouldReturnArtWorks() {
        String ownerName = "ownerName";
        List<ArtWork> artWorks = Arrays.asList(createArtWork(), createArtWork());
        when(artWorkService.findByOwnerName(ownerName)).thenReturn(Flux.fromIterable(artWorks));
        when(artWorkService.mapArtWorkToResponse(any(ArtWork.class))).thenReturn(Mono.just(createArtWorkResponseLeerAttr()));

        Flux<ArtWorkResponse> response = artWorkController.getArtworksByParam(null, null, ownerName, null, null, null, null, null, null);
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void getArtworksByParam_withMaterials_shouldReturnArtWorks() {
        List<String> materials = Arrays.asList("material1", "material2");
        List<ArtWork> artWorks = Arrays.asList(createArtWork(), createArtWork());
        when(artWorkService.findByMaterialsIn(materials)).thenReturn(Flux.fromIterable(artWorks));
        when(artWorkService.mapArtWorkToResponse(any(ArtWork.class))).thenReturn(Mono.just(createArtWorkResponseLeerAttr()));

        Flux<ArtWorkResponse> response = artWorkController.getArtworksByParam(null, null, null, materials, null, null, null, null, null);
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void getArtworksByParam_withTags_shouldReturnArtWorks() {
        List<String> tags = Arrays.asList("tag1", "tag2");
        List<ArtWork> artWorks = Arrays.asList(createArtWork(), createArtWork());
        when(artWorkService.findByTagsIn(tags)).thenReturn(Flux.fromIterable(artWorks));
        when(artWorkService.mapArtWorkToResponse(any(ArtWork.class))).thenReturn(Mono.just(createArtWorkResponseLeerAttr()));

        Flux<ArtWorkResponse> response = artWorkController.getArtworksByParam(null, null, null, null, tags, null, null, null, null);
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }


    @Test
    void getArtworksByParam_withPriceLessThan_shouldReturnArtWorks() {
        double priceLessThan = 100.0;
        List<ArtWork> artWorks = Arrays.asList(createArtWork(), createArtWork());
        when(artWorkService.findByPriceLessThan(priceLessThan)).thenReturn(Flux.fromIterable(artWorks));
        when(artWorkService.mapArtWorkToResponse(any(ArtWork.class))).thenReturn(Mono.just(createArtWorkResponseLeerAttr()));

        Flux<ArtWorkResponse> response = artWorkController.getArtworksByParam(null, null, null, null, null, null, priceLessThan, null, null);
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void getArtworksByParam_withPriceBetween_shouldReturnArtWorks() {
        double minPrice = 50.0;
        double maxPrice = 150.0;
        List<ArtWork> artWorks = Arrays.asList(createArtWork(), createArtWork());
        when(artWorkService.findByPriceBetween(minPrice, maxPrice)).thenReturn(Flux.fromIterable(artWorks));
        when(artWorkService.mapArtWorkToResponse(any(ArtWork.class))).thenReturn(Mono.just(createArtWorkResponseLeerAttr()));

        Flux<ArtWorkResponse> response = artWorkController.getArtworksByParam(null, null, null, null, null, null, null, maxPrice, minPrice);
        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();
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
    private ArtWorkResponse createArtWorkResponse(ArtWork artWork) {
        ArtWorkResponse artWorkResponse = new ArtWorkResponse();
        // Set necessary properties for the artWorkResponse based on the given artWork
        return artWorkResponse;
    }

    private ArtWorkResponse createArtWorkResponseLeerAttr() {
        ArtWorkResponse artWorkResponse = new ArtWorkResponse();
        // Set necessary properties for the artWorkResponse
        return artWorkResponse;
    }

    private FilePart createFilePart() {
        FilePart filePart = mock(FilePart.class);
        // Set necessary properties or behavior for the filePart mock
        return filePart;
    }

    private Flux<FilePart> createFileParts() {
        Flux<FilePart> fileParts = Flux.just(createFilePart(), createFilePart(), createFilePart());
        // Set necessary properties or behavior for the fileParts
        return fileParts;
    }

    private ArtWorkUpdateRequest createArtWorkUpdateRequest() {
        ArtWorkUpdateRequest artworkUpdateRequest = new ArtWorkUpdateRequest();
        // Set necessary properties for the artworkUpdateRequest
        return artworkUpdateRequest;
    }

    // Provide a test implementation for mapArtWorkToResponse method
    private Mono<ArtWorkResponse> mapArtWorkToResponse(ArtWork artWork) {
        // Implement the mapping logic here
        ArtWorkResponse artWorkResponse = new ArtWorkResponse();
        // Set necessary properties for the artWorkResponse based on the given artWork
        return Mono.just(artWorkResponse);
    }

}
