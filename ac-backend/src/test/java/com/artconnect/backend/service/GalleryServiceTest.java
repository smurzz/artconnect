package com.artconnect.backend.service;
import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.controller.response.ArtWorkResponse;
import com.artconnect.backend.controller.response.GalleryResponse;
import com.artconnect.backend.model.gallery.Gallery;
import com.artconnect.backend.model.gallery.GalleryCategory;
import com.artconnect.backend.model.user.Role;
import com.artconnect.backend.model.user.User;
import com.artconnect.backend.repository.GalleryRepository;
import com.artconnect.backend.service.ArtWorkService;
import com.artconnect.backend.service.ImageService;
import com.artconnect.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class GalleryServiceTest {
    @Mock
    private GalleryRepository galleryRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserService userService;
    @Mock
    private ArtWorkService artWorkService;
    @Mock
    private ImageService imageService;

    private GalleryService galleryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        galleryService = new GalleryService(galleryRepository, jwtService, userService, artWorkService, imageService);
    }

    @Test
    void findAll_shouldReturnAllGalleries() {
        // Given
        Gallery gallery1 = new Gallery();
        Gallery gallery2 = new Gallery();
        when(galleryRepository.findAll()).thenReturn(Flux.just(gallery1, gallery2));

        // When
        Flux<Gallery> result = galleryService.findAll();

        // Then
        assertIterableEquals(List.of(gallery1, gallery2), result.toIterable());
        verify(galleryRepository).findAll();
    }

    @Test
    void findByCategoriesIn_shouldReturnGalleriesWithMatchingCategories() {
        // Given
        List<GalleryCategory> categories = List.of(GalleryCategory.PRINT, GalleryCategory.PAINTING);
        Gallery gallery1 = new Gallery();
        Gallery gallery2 = new Gallery();
        when(galleryRepository.findByCategoriesIn(categories)).thenReturn(Flux.just(gallery1, gallery2));

        // When
        Flux<Gallery> result = galleryService.findByCategoriesIn(categories);

        // Then
        assertIterableEquals(List.of(gallery1, gallery2), result.toIterable());
        verify(galleryRepository).findByCategoriesIn(categories);
    }

    @Test
    void findById_shouldReturnGalleryWhenFound() {
        // Given
        String id = "123";
        Gallery gallery = new Gallery();
        when(galleryRepository.findById(id)).thenReturn(Mono.just(gallery));

        // When
        Mono<Gallery> result = galleryService.findById(id);

        // Then
        assertEquals(gallery, result.block());
        verify(galleryRepository).findById(id);
    }

    @Test
    void findById_shouldThrowExceptionWhenNotFound() {
        // Given
        String id = "123";
        when(galleryRepository.findById(id)).thenReturn(Mono.empty());

        // When
        Mono<Gallery> result = galleryService.findById(id);

        // Then
        assertThrows(ResponseStatusException.class, result::block);
        verify(galleryRepository).findById(id);
    }

    @Test
    void create_shouldCreateGalleryAndSetOwner() {
        // Given
        String authorization = "Bearer token";
        String userEmail = "user@example.com";
        String userId = "user123";
        Gallery gallery = new Gallery();
        gallery.setOwnerId(userId);
        when(jwtService.extractUsername("token")).thenReturn(userEmail);
        when(userService.findByEmail(userEmail)).thenReturn(Mono.just(User.builder().id(userId).email(userEmail).build()));
        when(galleryRepository.save(gallery)).thenReturn(Mono.just(gallery));
        when(userService.update(eq(userId), any(User.class), eq(authorization))).thenReturn(Mono.empty());

        // When
        Mono<Gallery> result = galleryService.create(gallery, authorization);

        // Then
        assertEquals(gallery, result.block());
        verify(userService).findByEmail(userEmail);
        verify(galleryRepository).save(gallery);
        verify(userService).update(eq(userId), any(User.class), eq(authorization));
    }

    @Test
    void create_shouldThrowExceptionWhenUserNotFound() {
        // Given
        String authorization = "Bearer token";
        String userEmail = "user@example.com";
        Gallery gallery = new Gallery();
        when(jwtService.extractUsername("token")).thenReturn(userEmail);
        when(userService.findByEmail(userEmail)).thenReturn(Mono.empty());

        // When
        Mono<Gallery> result = galleryService.create(gallery, authorization);

        // Then
        assertThrows(ResponseStatusException.class, result::block);
        verify(userService).findByEmail(userEmail);
        verifyNoMoreInteractions(userService);
        verifyNoInteractions(galleryRepository);
    }

    @Test
    void update_shouldUpdateGalleryWhenUserIsOwner() {
        // Given
        String id = "123";
        String authorization = "Bearer token";
        String userEmail = "user@example.com";
        String userId = "user123";
        Gallery gallery = new Gallery();
        gallery.setOwnerId(userId);
        Gallery updatedGallery = new Gallery();
        updatedGallery.setId(id);
        updatedGallery.setOwnerId(userId);
        when(jwtService.extractUsername("token")).thenReturn(userEmail);
        when(userService.findByEmail(userEmail)).thenReturn(Mono.just(User.builder().id(userId).email(userEmail).build()));
        when(galleryRepository.findById(id)).thenReturn(Mono.just(gallery));
        when(galleryRepository.save(gallery)).thenReturn(Mono.just(updatedGallery));

        // When
        Mono<Gallery> result = galleryService.update(id, gallery, authorization);

        // Then
        assertEquals(updatedGallery, result.block());
        verify(jwtService).extractUsername("token");
        verify(userService).findByEmail(userEmail);
        verify(galleryRepository).findById(id);
        verify(galleryRepository).save(gallery);
    }

    @Test
    void update_shouldThrowExceptionWhenUserIsNotOwner() {
        // Given
        String id = "123";
        String authorization = "Bearer token";
        String userEmail = "user@example.com";
        String userId = "user123";
        Gallery gallery = new Gallery();
        gallery.setOwnerId("otherUser123");
        when(jwtService.extractUsername("token")).thenReturn(userEmail);
        when(userService.findByEmail(userEmail)).thenReturn(Mono.just(User.builder().id(userId).email(userEmail).build()));
        when(galleryRepository.findById(id)).thenReturn(Mono.just(gallery));

        // When
        Mono<Gallery> result = galleryService.update(id, gallery, authorization);

        // Then
        assertThrows(ResponseStatusException.class, result::block);
        verify(jwtService).extractUsername("token");
        verify(userService).findByEmail(userEmail);
        verify(galleryRepository).findById(id);
        verifyNoMoreInteractions(galleryRepository);
    }

    @Test
    void delete_shouldDeleteGalleryWhenUserIsOwner() {
        // Given
        String id = "123";
        String authorization = "Bearer token";
        String userEmail = "user@example.com";
        String userId = "user123";
        Gallery gallery = new Gallery();
        gallery.setOwnerId(userId);
        when(jwtService.extractUsername("token")).thenReturn(userEmail);
        when(userService.findByEmail(userEmail)).thenReturn(Mono.just(User.builder().id(userId).email(userEmail).build()));
        when(galleryRepository.findById(id)).thenReturn(Mono.just(gallery));
        when(galleryRepository.delete(gallery)).thenReturn(Mono.empty());
        when(userService.findById(userId)).thenReturn(Mono.just(User.builder().id(userId).galleryId("").build()));
        when(userService.update(userId, User.builder().id(userId).galleryId("").build(), authorization)).thenReturn(Mono.empty());

        // When
        Mono<Void> result = galleryService.delete(id, authorization);

        // Then
        assertDoesNotThrow(() -> result.block());
        verify(jwtService).extractUsername("token");
        verify(userService).findByEmail(userEmail);
        verify(galleryRepository).findById(id);
        verify(galleryRepository).delete(gallery);
        verify(userService).findById(userId);
        verify(userService).update(userId, User.builder().id(userId).galleryId("").build(), authorization);
    }

//    @Test
//    void delete_shouldDeleteGalleryAndArtworksWhenUserIsAdmin() {
//        // Given
//        String id = "123";
//        String authorization = "Bearer token";
//        String userEmail = "admin@example.com";
//        String userId = "admin123";
//        Gallery gallery = new Gallery();
//        gallery.setOwnerId("user123");
//        gallery.setArtworkIds(List.of("artwork1", "artwork2"));
//        when(jwtService.extractUsername("token")).thenReturn(userEmail);
//        when(userService.findByEmail(userEmail)).thenReturn(Mono.just(User.builder().id(userId).email(userEmail).role(Role.ADMIN).build()));
//        when(galleryRepository.findById(id)).thenReturn(Mono.just(gallery));
//        when(artWorkService.deleteAllByIds(gallery.getArtworkIds(), authorization)).thenReturn(Mono.empty());
//        when(galleryRepository.delete(gallery)).thenReturn(Mono.empty());
//        when(userService.findById("user123")).thenReturn(Mono.just(User.builder().id("user123").galleryId("").build()));
//        when(userService.update("user123", User.builder().id("user123").galleryId("").build(), authorization)).thenReturn(Mono.empty());
//
//        // When
//        Mono<Void> result = galleryService.delete(id, authorization);
//
//        // Then
//        StepVerifier.create(result)
//                .expectComplete()
//                .verify();
//
//        verify(jwtService).extractUsername("token");
//        verify(userService).findByEmail(userEmail);
//        verify(galleryRepository).findById(id);
//        verify(artWorkService).deleteAllByIds(gallery.getArtworkIds(), authorization);
//        verify(galleryRepository).delete(gallery);
//        verify(userService).findById("user123");
//        verify(userService).update("user123", User.builder().id("user123").galleryId("").build(), authorization);
//    }
    @Test
    void delete_shouldThrowExceptionWhenUserIsNotOwnerOrAdmin() {
        // Given
        String id = "123";
        String authorization = "Bearer token";
        String userEmail = "user@example.com";
        String userId = "user123";
        Gallery gallery = new Gallery();
        gallery.setOwnerId("otherUser123");
        when(jwtService.extractUsername("token")).thenReturn(userEmail);
        when(userService.findByEmail(userEmail)).thenReturn(Mono.just(User.builder().id(userId).email(userEmail).build()));
        when(galleryRepository.findById(id)).thenReturn(Mono.just(gallery));

        // When
        Mono<Void> result = galleryService.delete(id, authorization);

        // Then
        assertThrows(ResponseStatusException.class, result::block);
        verify(jwtService).extractUsername("token");
        verify(userService).findByEmail(userEmail);
        verify(galleryRepository).findById(id);
        verifyNoMoreInteractions(galleryRepository);
    }

}
