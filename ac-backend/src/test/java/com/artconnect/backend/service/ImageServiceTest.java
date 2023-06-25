package com.artconnect.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.model.Image;
import com.artconnect.backend.repository.ImageRepository;
import com.artconnect.backend.validation.ImageValidation;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ImageServiceTest {

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

    @Test
    void addPhoto_ValidImage_ReturnsSavedImage() {
        FilePart filePart = mock(FilePart.class);
        DataBuffer dataBuffer = mock(DataBuffer.class);
        DataBufferFactory dataBufferUtils = mock(DataBufferFactory.class);
        Long sizeFile = 4718592L;
        HttpHeaders headers = mock(HttpHeaders.class);

        when(dataBuffer.factory()).thenReturn(dataBufferUtils);
        when(filePart.content()).thenReturn(Flux.fromIterable(Collections.singletonList(dataBuffer)));
        when(dataBufferUtils.join(Collections.singletonList(dataBuffer))).thenReturn(dataBuffer);
        when(filePart.headers()).thenReturn(headers);
        when(filePart.headers().getContentType()).thenReturn(MediaType.IMAGE_JPEG);
        when(filePart.filename()).thenReturn("ImageFile.jpeg");

        byte[] bytes = new byte[dataBuffer.readableByteCount()];
        dataBuffer.read(bytes);
        Binary imageRequestBytes = new Binary(BsonBinarySubType.BINARY, bytes);

        Image image = Image.builder()
                .image(imageRequestBytes)
                .title(filePart.filename())
                .contentType("image/jpeg")
                .build();

        when(imageValidation.validFile()).thenReturn(true);
        when(imageRepository.save(any(Image.class))).thenReturn(Mono.just(image));

        Mono<Image> result = imageService.addPhoto(Mono.just(filePart), sizeFile);

        StepVerifier.create(result)
                .expectNext(image)
                .expectComplete()
                .verify();

    }

    @Test
    void addPhoto_InvalidContentType_ReturnsBadRequestError() {
        FilePart filePart = mock(FilePart.class);
        DataBuffer dataBuffer = mock(DataBuffer.class);
        DataBufferFactory dataBufferUtils = mock(DataBufferFactory.class);
        Long sizeFile = 4718592L;
        HttpHeaders headers = mock(HttpHeaders.class);

        when(dataBuffer.factory()).thenReturn(dataBufferUtils);
        when(filePart.content()).thenReturn(Flux.fromIterable(Collections.singletonList(dataBuffer)));
        when(dataBufferUtils.join(Collections.singletonList(dataBuffer))).thenReturn(dataBuffer);
        when(filePart.headers()).thenReturn(headers);
        when(filePart.headers().getContentType()).thenReturn(MediaType.APPLICATION_PDF); // Invalid content type
        when(filePart.filename()).thenReturn("ImageFile.jpeg");

        when(imageValidation.validFile()).thenReturn(false); // Invalid file validation

        Mono<Image> result = imageService.addPhoto(Mono.just(filePart), sizeFile);

        StepVerifier.create(result)
                .expectErrorMatches(throwable ->
                        throwable instanceof ResponseStatusException
                                && ((ResponseStatusException) throwable).getStatusCode() == HttpStatus.BAD_REQUEST)
                .verify();
    }
    
    @Test
    void addPhoto_InvalidContentSize_ReturnsBadRequestError()  {
    	 FilePart filePart = mock(FilePart.class);
         DataBuffer dataBuffer = mock(DataBuffer.class);
         DataBufferFactory dataBufferUtils = mock(DataBufferFactory.class);
         Long sizeFile = 15728640L;	// Invalid content size 15728640
         HttpHeaders headers = mock(HttpHeaders.class);

         when(dataBuffer.factory()).thenReturn(dataBufferUtils);
         when(filePart.content()).thenReturn(Flux.fromIterable(Collections.singletonList(dataBuffer)));
         when(dataBufferUtils.join(Collections.singletonList(dataBuffer))).thenReturn(dataBuffer);
         when(filePart.headers()).thenReturn(headers);
         when(filePart.headers().getContentType()).thenReturn(MediaType.IMAGE_JPEG); 
         when(filePart.filename()).thenReturn("ImageFile.jpeg");

         when(imageValidation.validFile()).thenReturn(false); // Invalid file validation

         Mono<Image> result = imageService.addPhoto(Mono.just(filePart), sizeFile);
         
         StepVerifier.create(result)
         .expectErrorSatisfies(error -> {
         	assert error instanceof ResponseStatusException;
             ResponseStatusException responseError = (ResponseStatusException) error;
             assertEquals(responseError.getStatusCode(), HttpStatus.BAD_REQUEST);
             assertEquals(responseError.getMessage(), "400 BAD_REQUEST \"Image is not valid\"");
         })
         .verify();
    }
}