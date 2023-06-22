package com.artconnect.backend.controller;



import com.artconnect.backend.model.Image;
import com.artconnect.backend.service.ImageService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.verify;

public class ImageControllerTest {





    @Mock
    private ImageService imageService;

    @InjectMocks
    private ImageController imageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageService = mock(ImageService.class);
        imageController = new ImageController(imageService);
    }

//    @Test
//    void addPhoto_ReturnsResponseEntityWithImageBytes() throws Exception {
//        // Arrange
//        byte[] imageData = {0x11, 0x22, 0x33};
//        Binary imageBinary = new Binary(BsonBinarySubType.BINARY, ByteBuffer.wrap(imageData).array());
//        Image image = Image.builder()
//                .id("123")
//                .image(imageBinary)
//                .title("test.jpg")
//                .contentType(MediaType.IMAGE_JPEG_VALUE)
//                .build();
//
//        when(imageService.addPhoto(any(), anyLong())).thenReturn(Mono.just(image));
//
//        FilePart filePart = mock(FilePart.class);
//        when(filePart.filename()).thenReturn("test.jpg");
//        when(filePart.headers()).thenReturn(mock(HttpHeaders.class));
//
//        Mono<FilePart> file = Mono.just(filePart);
//        Long fileSize = 100L;
//
//        // Act
//        Mono<ResponseEntity<byte[]>> result = imageController.addPhoto(file, fileSize);
//
//        // Assert
//        ResponseEntity<byte[]> response = result.block();
//        assertEquals(MediaType.IMAGE_JPEG, response.getHeaders().getContentType());
//        assertEquals("attachment; filename=test.jpg", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
//        assertEquals(imageData, response.getBody());
//
//        verify(imageService, times(1)).addPhoto(any(), anyLong());
//    }
//
//
//    @Test
//    void getPhoto_shouldReturnImage() {
//        // Arrange
//        String id = "123";
//        byte[] imageData = "test-image-data".getBytes();
//        Image image = new Image();
//        image.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        image.setTitle("test-image.jpg");
//
//        when(imageService.getPhoto(id)).thenReturn(Mono.just(image));
//
//        // Act
//        Mono<ResponseEntity<byte[]>> result = imageController.getPhoto(id);
//
//        // Assert
//        StepVerifier.create(result)
//                .consumeNextWith(responseEntity -> {
//                    HttpHeaders headers = responseEntity.getHeaders();
//                    assertEquals(MediaType.IMAGE_JPEG_VALUE, headers.getContentType().toString());
//                    assertEquals("attachment; filename=test-image.jpg", headers.getFirst("Content-Disposition"));
//
//                    byte[] body = responseEntity.getBody();
//                    assertEquals(imageData, body);
//                })
//                .expectComplete()
//                .verify();
//
//        verify(imageService, times(1)).getPhoto(id);
//    }
}
