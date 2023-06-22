package com.artconnect.backend.controller;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.artconnect.backend.model.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import com.artconnect.backend.service.ImageService;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.AssertionErrors.fail;
import static org.mockito.Mockito.never;

public class ImageControllerTest {

    private ImageService imageService;
    private ImageController imageController;

    @BeforeEach
    public void setup() {
        imageService = mock(ImageService.class);
        imageController = new ImageController(imageService);
    }

    @Test
    public void testAddPhoto() throws IOException {
        // Mock file and file size
        FilePart filePart = mock(FilePart.class);
        Mono<FilePart> filePartMono = Mono.just(filePart);
        Long fileSize = 1000L;

        // Mock image response
        Image image = new Image();
        image.setContentType("image/jpeg");
        image.setTitle("test.jpg");

        // Mock image service
        when(imageService.addPhoto(filePartMono, fileSize)).thenReturn(Mono.just(image));

        // Invoke controller method
        Mono<ResponseEntity<byte[]>> result = imageController.addPhoto(filePartMono, fileSize);

        // Verify the response
        result.subscribe(responseEntity -> {
            HttpHeaders headers = responseEntity.getHeaders();
            MediaType contentType = headers.getContentType();
            String contentDisposition = headers.getFirst("Content-Disposition");

            // Add assertions based on your requirements
            // Example assertions:
            assertEquals(MediaType.IMAGE_JPEG, contentType);
            assertEquals("attachment; filename=test.jpg", contentDisposition);
        });
    }

    @Test
    public void testGetPhoto() {
        // Mock image ID
        String id = "123";

        // Mock image response
        Image image = new Image();
        image.setContentType("image/jpeg");
        image.setTitle("test.jpg");

        // Mock image service
        when(imageService.getPhoto(id)).thenReturn(Mono.just(image));

        // Invoke controller method
        Mono<ResponseEntity<byte[]>> result = imageController.getPhoto(id);

        // Verify the response
        result.subscribe(responseEntity -> {
            HttpHeaders headers = responseEntity.getHeaders();
            MediaType contentType = headers.getContentType();
            String contentDisposition = headers.getFirst("Content-Disposition");

            // Add assertions based on your requirements
            // Example assertions:
            assertEquals(MediaType.IMAGE_JPEG, contentType);
            assertEquals("attachment; filename=test.jpg", contentDisposition);
        });
    }

    @Test
    public void testGetPhotoNotFound() {
        // Mock image ID
        String id = "456";

        // Mock empty image response (image not found)
        when(imageService.getPhoto(id)).thenReturn(Mono.empty());

        // Invoke controller method
        Mono<ResponseEntity<byte[]>> result = imageController.getPhoto(id);

        // Verify the error response
        result.subscribe(responseEntity -> {
            assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        });
    }

    @Test
    public void testAddPhotoThrowsIOException() throws IOException {
        // Mock file and file size
        FilePart filePart = mock(FilePart.class);
        Mono<FilePart> filePartMono = Mono.just(filePart);
        Long fileSize = 1000L;

        // Mock IOException from the service
        when(imageService.addPhoto(filePartMono, fileSize)).thenReturn(Mono.error(new IOException("Failed to add photo.")));

        // Invoke controller method
        Mono<ResponseEntity<byte[]>> result = imageController.addPhoto(filePartMono, fileSize);

        // Verify the error response
        result.subscribe(responseEntity -> {
            fail("Expected an IOException.");
        }, error -> {
            assertTrue(error instanceof IOException);
            assertEquals("Failed to add photo.", error.getMessage());
        });
    }

    @Test
    public void testGetPhotoThrowsException() {
        // Mock image ID
        String id = "123";

        // Mock RuntimeException from the service
        when(imageService.getPhoto(id)).thenReturn(Mono.error(new RuntimeException("Failed to get photo.")));

        // Invoke controller method
        Mono<ResponseEntity<byte[]>> result = imageController.getPhoto(id);

        // Verify the error response
        result.subscribe(responseEntity -> {
            fail("Expected a RuntimeException.");
        }, error -> {
            assertTrue(error instanceof RuntimeException);
            assertEquals("Failed to get photo.", error.getMessage());
        });
    }

    @Test
    public void testAddPhotoResponseEntity() throws IOException {
        // Mock file and file size
        FilePart filePart = mock(FilePart.class);
        Mono<FilePart> filePartMono = Mono.just(filePart);
        Long fileSize = 1000L;

        // Mock image response
        Image image = new Image();
        image.setContentType("image/jpeg");
        image.setTitle("test.jpg");
        byte[] imageData = { 1, 2, 3 }; // Mock image data

        // Mock image service
        when(imageService.addPhoto(filePartMono, fileSize)).thenReturn(Mono.just(image));

        // Invoke controller method
        Mono<ResponseEntity<byte[]>> result = imageController.addPhoto(filePartMono, fileSize);

        // Verify the response
        result.subscribe(responseEntity -> {
            HttpHeaders headers = responseEntity.getHeaders();
            MediaType contentType = headers.getContentType();
            String contentDisposition = headers.getFirst("Content-Disposition");
            byte[] responseBody = responseEntity.getBody();

            // Add assertions based on your requirements
            // Example assertions:
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(MediaType.IMAGE_JPEG, contentType);
            assertEquals("attachment; filename=test.jpg", contentDisposition);
            assertArrayEquals(imageData, responseBody);
        });

        // Verify that the image service was called
        verify(imageService).addPhoto(filePartMono, fileSize);
    }

    @Test
    public void testAddPhotoResponseEntityAddPhoto() throws IOException {
        // Mock file and file size
        FilePart filePart = mock(FilePart.class);
        Mono<FilePart> filePartMono = Mono.just(filePart);
        Long fileSize = 1000L;

        // Mock image response
        Image image = new Image();
        image.setContentType("image/jpeg");
        image.setTitle("test.jpg");
        byte[] imageData = { 1, 2, 3 }; // Mock image data

        // Mock image service
        when(imageService.addPhoto(filePartMono, fileSize)).thenReturn(Mono.just(image));

        // Invoke controller method
        Mono<ResponseEntity<byte[]>> result = imageController.addPhoto(filePartMono, fileSize);

        // Verify the response
        result.subscribe(responseEntity -> {
            HttpHeaders headers = responseEntity.getHeaders();
            MediaType contentType = headers.getContentType();
            String contentDisposition = headers.getFirst("Content-Disposition");
            byte[] responseBody = responseEntity.getBody();

            // Add assertions based on your requirements
            // Example assertions:
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(MediaType.IMAGE_JPEG, contentType);
            assertEquals("attachment; filename=test.jpg", contentDisposition);
            assertArrayEquals(imageData, responseBody);
        });

        // Verify that the image service was called
        verify(imageService).addPhoto(filePartMono, fileSize);
    }
}