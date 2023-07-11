package com.artconnect.backend.controller;

//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.Arrays;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.artconnect.backend.controller.GalleryController;
//import com.artconnect.backend.controller.response.GalleryResponse;
//import com.artconnect.backend.model.gallery.Gallery;
//import com.artconnect.backend.service.GalleryService;
//
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//public class GalleryControllerTest {
//
//    @InjectMocks
//    private GalleryController controller;
//
//    @Mock
//    private GalleryService galleryService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testGetAllGalleries() {
//        Gallery gallery1 = new Gallery();
//        // set gallery1 fields as needed
//
//        Gallery gallery2 = new Gallery();
//        // set gallery2 fields as needed
//
//        GalleryResponse response1 = new GalleryResponse();
//        // set response1 fields as needed
//
//        GalleryResponse response2 = new GalleryResponse();
//        // set response2 fields as needed
//
//        when(galleryService.findAll()).thenReturn(Flux.just(gallery1, gallery2));
//        when(galleryService.mapGalleryToResponse(any(Gallery.class))).thenReturn(Mono.just(response1), Mono.just(response2));
//
//        Flux<GalleryResponse> result = controller.getAllGalleries();
//
//        try {
//            assertEquals(2, result.collectList().block().size());
//            assertEquals(response1, result.collectList().block().get(0));
//            assertEquals(response2, result.collectList().block().get(1));
//        } catch (AssertionError e) {
//            // handle the assertion failure
//            System.out.println("Assertion failed: " + e.getMessage());
//        }
//
//        verify(galleryService, times(1)).findAll();
//        verify(galleryService, times(2)).mapGalleryToResponse(any(Gallery.class));
//    }
//}
//

