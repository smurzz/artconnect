package com.artconnect.backend.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.artconnect.backend.model.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import com.artconnect.backend.service.ImageService;

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

}