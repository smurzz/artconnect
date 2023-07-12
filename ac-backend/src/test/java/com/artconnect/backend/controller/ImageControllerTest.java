package com.artconnect.backend.controller;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.config.ApplicationConfig;
import com.artconnect.backend.config.SecurityConfig;
import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.model.Image;
import com.artconnect.backend.repository.UserRepository;
import com.artconnect.backend.service.ImageService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = ImageController.class)
@Import({ SecurityConfig.class, ApplicationConfig.class })
public class ImageControllerTest {
	
	@MockBean
    private JwtService jwtService;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
    private ImageService imageService;
    
    @InjectMocks
    private ImageController imageController;
    
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
    	MockitoAnnotations.openMocks(this);
    	imageController = new ImageController(imageService);
    }

	@Test
	@WithMockUser
	public void testAddPhoto() {
		// Mock file part
	    FilePart filePart = mock(FilePart.class);

	    // Prepare image data
	    byte[] imageData = "test".getBytes(StandardCharsets.UTF_8);
	    DefaultDataBuffer dataBuffer = DefaultDataBufferFactory.sharedInstance.wrap(ByteBuffer.wrap(imageData));
	    Flux<DataBuffer> body = Flux.just(dataBuffer);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG);

	    // Mock image response
	    Image image = new Image();
	    image.setContentType(MediaType.IMAGE_JPEG_VALUE);
	    image.setTitle("test.jpg");
	    image.setImage(new Binary(imageData));

	    // Mock image service
	    when(filePart.headers()).thenReturn(headers);
	    when(filePart.filename()).thenReturn("test.jpg");
	    when(filePart.content()).thenReturn(body);
	    when(imageService.addPhoto(any(Mono.class))).thenReturn(Mono.just(image));

	    // Send request and verify response
	    webTestClient = WebTestClient.bindToController(imageController).build();

	    webTestClient.post()
	            .uri("/images/add")
	            .header("Content-Length", String.valueOf(imageData.length))
	            .contentType(MediaType.MULTIPART_FORM_DATA)
	            .body(BodyInserters.fromMultipartData("file", filePart))
	            .exchange()
	            .expectStatus().isOk()
	            .expectHeader().contentType(MediaType.IMAGE_JPEG)
	            .expectHeader().contentDisposition(ContentDisposition.builder("attachment")
	                    .filename("test.jpg").build())
	            .expectBody(byte[].class)
	            .value(response -> assertArrayEquals(response, image.getImage().getData()));
	}
	

    @Test
    @WithMockUser
    public void testAddPhotoImageExtensionIsFalse() {
    	// Mock file part
	    FilePart filePart = mock(FilePart.class);

	    // Prepare image data
	    byte[] imageData = "test".getBytes(StandardCharsets.UTF_8);
	    DefaultDataBuffer dataBuffer = DefaultDataBufferFactory.sharedInstance.wrap(ByteBuffer.wrap(imageData));
	    Flux<DataBuffer> body = Flux.just(dataBuffer);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);

	    // Mock image service
	    when(filePart.headers()).thenReturn(headers);
	    when(filePart.filename()).thenReturn("test.pdf");
	    when(filePart.content()).thenReturn(body);
	    when(imageService.addPhoto(any(Mono.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image is not valid")));

	    // Send request and verify response
	    webTestClient = WebTestClient.bindToController(imageController).build();

	    webTestClient.post()
	            .uri("/images/add")
	            .header("Content-Length", "4545565")
	            .contentType(MediaType.MULTIPART_FORM_DATA)
	            .body(BodyInserters.fromMultipartData("file", filePart))
	            .exchange()
	            .expectStatus().isBadRequest();
    }
    
    @Test
    @WithMockUser
    public void testAddPhotoImageSizeIsTooBig() {
    	// Mock file part
	    FilePart filePart = mock(FilePart.class);
	    
	    // Prepare image data
	    byte[] imageData = "test".getBytes(StandardCharsets.UTF_8);
	    DefaultDataBuffer dataBuffer = DefaultDataBufferFactory.sharedInstance.wrap(ByteBuffer.wrap(imageData));
	    Flux<DataBuffer> body = Flux.just(dataBuffer);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_PNG);
	    headers.setContentLength(4545545665L);

	    // Mock image service
	    when(filePart.headers()).thenReturn(headers);
	    when(filePart.filename()).thenReturn("test.png");
	    when(filePart.content()).thenReturn(body);
	    when(imageService.addPhoto(any(Mono.class))).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image is not valid")));

	    // Send request and verify response
	    webTestClient = WebTestClient.bindToController(imageController).build();

	    webTestClient.post()
	            .uri("/images/add")
	            .header("Content-Length", "4545545665")
	            .contentType(MediaType.MULTIPART_FORM_DATA)
	            .body(BodyInserters.fromMultipartData("file", filePart))
	            .exchange()
	            .expectStatus().isBadRequest();
    }
    

    @Test
    @WithMockUser
    public void testGetPhotoReturnsImage() {
        // Mock the image service
        Image image = Image.builder()
	        .id("imageId")
	        .title("image.jpg")
	        .contentType("image/jpeg")
	        .image(new Binary(new byte[]{}))
	        .build();
        
        when(imageService.getPhoto(anyString())).thenReturn(Mono.just(image));

        // Perform the request and verify the response
        webTestClient.get()
                .uri("/images/{id}", "imageId")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.valueOf("image/jpeg"))
                .expectHeader().valueEquals("Content-Disposition", "attachment; filename=image.jpg")
                .expectBody(byte[].class).isEqualTo(image.getImage().getData());
    }

    @Test
    @WithMockUser
    public void testGetPhotoReturnsNotFound() {
        // Mock the image service to return an empty Mono
        when(imageService.getPhoto(anyString())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));

        // Perform the request and verify the response
        webTestClient.get()
                .uri("/images/{id}", "nonExistentId")
                .exchange()
                .expectStatus().isNotFound();
    }
    
    @Test
    @WithMockUser
    public void testGetPhotoThrowsException() {
        // Mock the image service to throw an exception
        when(imageService.getPhoto(anyString())).thenReturn(Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)));

        // Perform the request and verify the response
        webTestClient.get()
                .uri("/images/{id}", "imageId")
                .exchange()
                .expectStatus().is5xxServerError();
    }



}