package com.artconnect.backend.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.artconnect.backend.controller.CommentController;
import com.artconnect.backend.controller.request.CommentRequest;
import com.artconnect.backend.controller.response.ArtWorkResponse;
import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.service.ArtWorkService;
import com.artconnect.backend.service.CommentService;

import reactor.core.publisher.Mono;

public class CommentControllerTest {

    @InjectMocks
    private CommentController controller;

    @Mock
    private CommentService commentService;

    @Mock
    private ArtWorkService artWorkService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateComment() {
        String id = "1";
        CommentRequest request = new CommentRequest();
        // set request fields as needed

        ArtWork artWork = new ArtWork();
        // set artWork fields as needed

        ArtWorkResponse response = new ArtWorkResponse();
        // set response fields as needed

        when(commentService.create(id, request)).thenReturn(Mono.just(artWork));
        when(artWorkService.mapArtWorkToResponse(artWork)).thenReturn(Mono.just(response));

        Mono<ArtWorkResponse> result = controller.createComment(id, request);

        assertEquals(response, result.block());
        verify(commentService, times(1)).create(id, request);
        verify(artWorkService, times(1)).mapArtWorkToResponse(artWork);
    }
}

