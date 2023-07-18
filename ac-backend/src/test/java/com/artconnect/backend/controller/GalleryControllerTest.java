package com.artconnect.backend.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.artconnect.backend.controller.request.GalleryRequest;
import com.artconnect.backend.controller.response.GalleryResponse;
import com.artconnect.backend.model.gallery.Gallery;
import com.artconnect.backend.model.gallery.GalleryCategory;
import com.artconnect.backend.service.GalleryService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class GalleryControllerTest {

    private GalleryService galleryService;
    private GalleryController galleryController;

    @BeforeEach
    public void setUp() {
        galleryService = mock(GalleryService.class);
        galleryController = new GalleryController(galleryService);
    }

    @Test
    public void testGetAllGalleries() {
        Gallery gallery1 = createGallery("1", "Gallery 1");
        Gallery gallery2 = createGallery("2", "Gallery 2");
        List<Gallery> galleries = List.of(gallery1, gallery2);
        GalleryResponse response1 = createGalleryResponse("1", "Gallery 1");
        GalleryResponse response2 = createGalleryResponse("2", "Gallery 2");
        when(galleryService.findAll()).thenReturn(Flux.fromIterable(galleries));
        when(galleryService.mapGalleryToPublicResponse(any())).thenAnswer(invocation -> {
            Gallery gallery = invocation.getArgument(0);
            return Mono.just(createGalleryResponse(gallery.getId(), gallery.getTitle()));
        });

        Flux<GalleryResponse> result = galleryController.getAllGalleries();

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getId().equals(response1.getId())
                        && response.getTitle().equals(response1.getTitle()))
                .expectNextMatches(response -> response.getId().equals(response2.getId())
                        && response.getTitle().equals(response2.getTitle()))
                .verifyComplete();
    }
    @Test
    public void testGetGalleriesByParam_WithCategories() {
        List<GalleryCategory> categories = List.of(GalleryCategory.PAINTING);
        Gallery gallery1 = createGallery("1", "Gallery 1");
        Gallery gallery2 = createGallery("2", "Gallery 2");
        List<Gallery> galleries = List.of(gallery1, gallery2);
        GalleryResponse response1 = createGalleryResponse("1", "Gallery 1");
        GalleryResponse response2 = createGalleryResponse("2", "Gallery 2");
        when(galleryService.findByCategoriesIn(categories)).thenReturn(Flux.fromIterable(galleries));
        when(galleryService.mapGalleryToPublicResponse(any())).thenAnswer(invocation -> {
            Gallery gallery = invocation.getArgument(0);
            return Mono.just(createGalleryResponse(gallery.getId(), gallery.getTitle()));
        });

        Flux<GalleryResponse> result = galleryController.getGalleriesByParam(categories);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getId().equals(response1.getId())
                        && response.getTitle().equals(response1.getTitle()))
                .expectNextMatches(response -> response.getId().equals(response2.getId())
                        && response.getTitle().equals(response2.getTitle()))
                .verifyComplete();
    }

    @Test
    public void testGetGalleriesByParam_WithoutCategories() {
        List<GalleryCategory> categories = new ArrayList<>();
        when(galleryService.findByCategoriesIn(categories)).thenReturn(Flux.empty());

        Flux<GalleryResponse> result = galleryController.getGalleriesByParam(categories);

        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    @Test
    public void testGetGalleryById() {
        String galleryId = "1";
        Gallery gallery = createGallery(galleryId, "Gallery 1");
        GalleryResponse response = createGalleryResponse(galleryId, "Gallery 1");
        when(galleryService.findById(galleryId)).thenReturn(Mono.just(gallery));
        when(galleryService.mapGalleryToPublicResponse(gallery)).thenReturn(Mono.just(response));

        Mono<GalleryResponse> result = galleryController.getGalleryById(galleryId);

        StepVerifier.create(result)
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    public void testCreateGallery() {
        String authorization = "Bearer token";
        GalleryRequest request = createGalleryRequest("Gallery 1");
        Gallery gallery = createGallery("1", "Gallery 1");
        GalleryResponse response = createGalleryResponse("1", "Gallery 1");
        when(galleryService.create(any(), eq(authorization))).thenReturn(Mono.just(gallery));
        when(galleryService.mapGalleryToResponse(gallery)).thenReturn(Mono.just(response));

        Mono<GalleryResponse> result = galleryController.createGallery(authorization, request);

        StepVerifier.create(result)
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    public void testUpdateMyGallery() {
        String galleryId = "1";
        String authorization = "Bearer token";
        GalleryRequest request = createGalleryRequest("Gallery 1");
        Gallery gallery = createGallery(galleryId, "Gallery 1");
        GalleryResponse response = createGalleryResponse(galleryId, "Gallery 1");
        when(galleryService.update(eq(galleryId), any(), eq(authorization))).thenReturn(Mono.just(gallery));
        when(galleryService.mapGalleryToResponse(gallery)).thenReturn(Mono.just(response));

        Mono<GalleryResponse> result = galleryController.updateMyGallery(galleryId, request, authorization);

        StepVerifier.create(result)
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    public void testDeleteGallery() {
        String galleryId = "1";
        String authorization = "Bearer token";
        when(galleryService.delete(galleryId, authorization)).thenReturn(Mono.empty());

        Mono<Void> result = galleryController.deleteGallery(galleryId, authorization);

        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    private Gallery createGallery(String id, String title) {
        return Gallery.builder()
                .id(id)
                .title(title)
                .build();
    }

    private GalleryRequest createGalleryRequest(String title) {
        GalleryRequest request = new GalleryRequest();
        request.setTitle(title);
        return request;
    }

    private GalleryResponse createGalleryResponse(String id, String title) {
        GalleryResponse response = new GalleryResponse();
        response.setId(id);
        response.setTitle(title);
        return response;
    }
}