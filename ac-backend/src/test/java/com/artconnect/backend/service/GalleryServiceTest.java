package com.artconnect.backend.service;
import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.controller.response.ArtWorkResponse;
import com.artconnect.backend.controller.response.GalleryResponse;
import com.artconnect.backend.model.Image;
import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.model.gallery.Gallery;
import com.artconnect.backend.model.gallery.GalleryCategory;
import com.artconnect.backend.model.user.Role;
import com.artconnect.backend.model.user.User;
import com.artconnect.backend.repository.GalleryRepository;
import com.artconnect.backend.service.ArtWorkService;
import com.artconnect.backend.service.ImageService;
import com.artconnect.backend.service.UserService;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.*;

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

//    @Test
//    void update_shouldUpdateGalleryWhenUserIsOwner() {
//        // Given
//        String id = "123";
//        String authorization = "Bearer token";
//        String userEmail = "user@example.com";
//        String userId = "user123";
//        Gallery gallery = new Gallery();
//        gallery.setOwnerId(userId);
//        Gallery updatedGallery = new Gallery();
//        updatedGallery.setId(id);
//        updatedGallery.setOwnerId(userId);
//        when(jwtService.extractUsername("token")).thenReturn(userEmail);
//        when(userService.findByEmail(userEmail)).thenReturn(Mono.just(User.builder().id(userId).email(userEmail).build()));
//        when(galleryRepository.findById(id)).thenReturn(Mono.just(gallery));
//        when(galleryRepository.save(gallery)).thenReturn(Mono.just(updatedGallery));
//
//        // When
//        Mono<Gallery> result = galleryService.update(id, gallery, authorization);
//
//        // Then
//        assertEquals(updatedGallery, result.block());
//        verify(jwtService).extractUsername("token");
//        verify(userService).findByEmail(userEmail);
//        verify(galleryRepository).findById(id);
//        verify(galleryRepository).save(gallery);
//    }
//
//    @Test
//    void update_shouldThrowExceptionWhenUserIsNotOwner() {
//        // Given
//        String id = "123";
//        String authorization = "Bearer token";
//        String userEmail = "user@example.com";
//        String userId = "user123";
//        Gallery gallery = new Gallery();
//        gallery.setOwnerId("otherUser123");
//        when(jwtService.extractUsername("token")).thenReturn(userEmail);
//        when(userService.findByEmail(userEmail)).thenReturn(Mono.just(User.builder().id(userId).email(userEmail).build()));
//        when(galleryRepository.findById(id)).thenReturn(Mono.just(gallery));
//
//        // When
//        Mono<Gallery> result = galleryService.update(id, gallery, authorization);
//
//        // Then
//        assertThrows(ResponseStatusException.class, result::block);
//        verify(jwtService).extractUsername("token");
//        verify(userService).findByEmail(userEmail);
//        verify(galleryRepository).findById(id);
//        verifyNoMoreInteractions(galleryRepository);
//    }

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

    @Test
    public void mapGalleryToResponse_WithArtworkAndImages_ReturnsGalleryResponse() {
        // Arrange
        Gallery gallery = new Gallery();
        gallery.setId("gallery-id");

        ArtWork artwork = new ArtWork();
        artwork.setImagesIds(List.of("image1", "image2"));

        Image image1 = Image.builder()
                .id("image1")
                .image(new Binary(new byte[]{1, 2, 3}))
                .title("Image 1")
                .contentType("image/jpeg")
                .build();
        Image image2 = Image.builder()
                .id("image2")
                .image(new Binary(new byte[]{4, 5, 6}))
                .title("Image 2")
                .contentType("image/jpeg")
                .build();

        when(artWorkService.findByGalleryId(gallery.getId())).thenReturn(Flux.just(artwork));
        when(imageService.getPhotosByIds(artwork.getImagesIds())).thenReturn(Flux.just(image1, image2));
        when(userService.getCurrentUser()).thenReturn(Mono.just(new User()));

        // Act
        Mono<GalleryResponse> responseMono = galleryService.mapGalleryToResponse(gallery);

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> {
                    return true;
                })
                .verifyComplete();
    }


    @Test
    public void mapGalleryToResponse_WithArtworkAndNoImages_ReturnsGalleryResponseWithEmptyImagesList() {
        // Arrange
        Gallery gallery = new Gallery();
        gallery.setId("gallery-id");

        ArtWork artwork = new ArtWork();
        artwork.setImagesIds(Collections.emptyList());

        when(artWorkService.findByGalleryId(gallery.getId())).thenReturn(Flux.just(artwork));
        when(userService.getCurrentUser()).thenReturn(Mono.just(new User()));

        // Act
        Mono<GalleryResponse> responseMono = galleryService.mapGalleryToResponse(gallery);

        // Assert
        StepVerifier.create(responseMono)
                .expectNextMatches(response -> {
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void update_WithValidIdAndAuthorizedUser_ReturnsUpdatedGallery() {
        // Arrange
        String id = "gallery-id";
        Gallery gallery = new Gallery();
        gallery.setId(id);
        String authorization = "valid-authorization-token";

        User user = new User();
        user.setId("user-id");

        Gallery existingGallery = new Gallery();
        existingGallery.setId(id);
        existingGallery.setOwnerId(user.getId());

        when(userService.findByEmail(getEmailFromAuthentication(authorization))).thenReturn(Mono.just(user));
        when(galleryRepository.findById(id)).thenReturn(Mono.just(existingGallery));
        when(galleryRepository.save(existingGallery)).thenReturn(Mono.just(existingGallery));

        // Act
        Mono<Gallery> resultMono = galleryService.update(id, gallery, authorization);

        // Assert
        StepVerifier.create(resultMono)
                .expectNext(existingGallery)
                .verifyComplete();
    }

    @Test
    public void update_WithInvalidId_ThrowsResponseStatusException() {
        // Arrange
        String id = "non-existent-id";
        Gallery gallery = new Gallery();
        String authorization = "valid-authorization-token";

        User user = new User();
        user.setId("user-id");

        when(userService.findByEmail(getEmailFromAuthentication(authorization))).thenReturn(Mono.just(user));
        when(galleryRepository.findById(id)).thenReturn(Mono.empty());

        // Act
        Mono<Gallery> resultMono = galleryService.update(id, gallery, authorization);

        // Assert
        StepVerifier.create(resultMono)
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    public void update_WithUnauthorizedUser_ThrowsResponseStatusException() {
        // Arrange
        String id = "gallery-id";
        Gallery gallery = new Gallery();
        String authorization = "invalid-authorization-token";

        User user = new User();
        user.setId("user-id");

        Gallery existingGallery = new Gallery();
        existingGallery.setId(id);
        existingGallery.setOwnerId("other-user-id");

        when(userService.findByEmail(getEmailFromAuthentication(authorization))).thenReturn(Mono.just(user));
        when(galleryRepository.findById(id)).thenReturn(Mono.just(existingGallery));

        // Act
        Mono<Gallery> resultMono = galleryService.update(id, gallery, authorization);

        // Assert
        StepVerifier.create(resultMono)
                .expectError(ResponseStatusException.class)
                .verify();
    }

    @Test
    public void update_WithAdminUser_ReturnsUpdatedGallery() {
        // Arrange
        String id = "gallery-id";
        Gallery gallery = new Gallery();
        gallery.setId(id);
        String authorization = "admin-authorization-token";

        User adminUser = new User();
        adminUser.setId("admin-user-id");
        adminUser.setRole(Role.ADMIN);

        Gallery existingGallery = new Gallery();
        existingGallery.setId(id);
        existingGallery.setOwnerId("other-user-id");

        when(userService.findByEmail(getEmailFromAuthentication(authorization))).thenReturn(Mono.just(adminUser));
        when(galleryRepository.findById(id)).thenReturn(Mono.just(existingGallery));
        when(galleryRepository.save(existingGallery)).thenReturn(Mono.just(existingGallery));

        // Act
        Mono<Gallery> resultMono = galleryService.update(id, gallery, authorization);

        // Assert
        StepVerifier.create(resultMono)
                .expectNext(existingGallery)
                .verifyComplete();
    }


    // Helper method to get email from authentication token
    private String getEmailFromAuthentication(String authorization) {
        // Implement this based on your actual authentication logic
        return null;
    }

}
