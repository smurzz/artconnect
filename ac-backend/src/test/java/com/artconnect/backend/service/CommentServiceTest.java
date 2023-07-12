package com.artconnect.backend.service;

import com.artconnect.backend.controller.request.CommentRequest;
import com.artconnect.backend.controller.request.CommentUpdateRequest;
import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.model.artwork.Comment;
import com.artconnect.backend.model.user.User;
import com.artconnect.backend.repository.ArtWorkRepository;
import com.artconnect.backend.service.ArtWorkService;
import com.artconnect.backend.service.CommentService;
import com.artconnect.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.*;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;



class CommentServiceTest {
    @Mock
    private ArtWorkRepository artWorkRepository;
    @Mock
    private ArtWorkService artWorkService;
    @Mock
    private UserService userService;

    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentService(artWorkRepository, artWorkService, userService);
    }

    @Test
    void createComment_Success() {
        // Mocking data
        String artworkId = "artworkId";
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setCommentText("New comment");

        ArtWork artwork = new ArtWork();
        artwork.setId(artworkId);
        artwork.setComments(new ArrayList<>());

        User currentUser = new User();
        currentUser.setId("userId");
        currentUser.setFirstname("John");

        when(userService.getCurrentUser()).thenReturn(Mono.just(currentUser));
        when(artWorkService.findById(artworkId)).thenReturn(Mono.just(artwork));
        when(artWorkRepository.save(artwork)).thenReturn(Mono.just(artwork));

        // Test the create method
        StepVerifier.create(commentService.create(artworkId, commentRequest))
                .expectNext(artwork)
                .verifyComplete();

        // Verify that the comment was added to the artwork
        Comment expectedComment = artwork.getComments().get(0);
        assertEquals("New comment", expectedComment.getText());
        assertEquals("userId", expectedComment.getAuthorId());
        assertEquals("John", expectedComment.getAuthorName());
    }

    @Test
    void updateComment_Success() {
        // Mocking data
        String artworkId = "artworkId";
        String commentId = "commentId";
        CommentUpdateRequest commentRequest = new CommentUpdateRequest();
        commentRequest.setCommentText("Updated comment");

        ArtWork artwork = new ArtWork();
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setAuthorId("userId");
        comment.setAuthorName("John");
        comment.setText("Old comment");
        artwork.setComments(List.of(comment));

        User currentUser = new User();
        currentUser.setId("userId");
        currentUser.setFirstname("John");

        when(userService.getCurrentUser()).thenReturn(Mono.just(currentUser));
        when(artWorkService.findById(artworkId)).thenReturn(Mono.just(artwork));
        when(artWorkRepository.save(artwork)).thenReturn(Mono.just(artwork));

        // Test the update method
        StepVerifier.create(commentService.update(artworkId, commentId, commentRequest))
                .expectNext(artwork)
                .verifyComplete();

        // Verify that the comment was updated
        Comment updatedComment = artwork.getComments().get(0);
        assertEquals("Updated comment", updatedComment.getText());
        assertTrue(updatedComment.isUpdated());
        assertNotNull(updatedComment.getUpdatedAt());
    }

    @Test
    void updateComment_Forbidden() {
        // Mocking data
        String artworkId = "artworkId";
        String commentId = "commentId";
        CommentUpdateRequest commentRequest = new CommentUpdateRequest();
        commentRequest.setCommentText("Updated comment");

        ArtWork artwork = new ArtWork();
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setAuthorId("anotherUserId");
        comment.setAuthorName("Jane");
        comment.setText("Old comment");
        artwork.setComments(List.of(comment));

        User currentUser = new User();
        currentUser.setId("userId");
        currentUser.setFirstname("John");

        when(userService.getCurrentUser()).thenReturn(Mono.just(currentUser));
        when(artWorkService.findById(artworkId)).thenReturn(Mono.just(artwork));

        // Test the update method and expect a Forbidden exception
        StepVerifier.create(commentService.update(artworkId, commentId, commentRequest))
                .expectErrorMatches(error ->
                        error instanceof ResponseStatusException &&
                                ((ResponseStatusException) error).getStatusCode().equals(HttpStatus.FORBIDDEN) &&
                                Objects.equals(error.getMessage(), "403 FORBIDDEN \"You are not allowed to update a comment for another user\""))
                .verify();

        // Verify that the comment was not updated
        Comment unchangedComment = artwork.getComments().get(0);
        assertEquals("Old comment", unchangedComment.getText());
        assertFalse(unchangedComment.isUpdated());
        assertNull(unchangedComment.getUpdatedAt());
    }

    @Test
    void updateComment_CommentNotFound() {
        // Mocking data
        String artworkId = "artworkId";
        String commentId = "nonExistentCommentId";
        CommentUpdateRequest commentRequest = new CommentUpdateRequest();
        commentRequest.setCommentText("Updated comment");

        ArtWork artwork = new ArtWork();

        User currentUser = new User();
        currentUser.setId("userId");
        currentUser.setFirstname("John");

        when(userService.getCurrentUser()).thenReturn(Mono.just(currentUser));
        when(artWorkService.findById(artworkId)).thenReturn(Mono.just(artwork));

        // Test the update method and expect a Not Found exception
        StepVerifier.create(commentService.update(artworkId, commentId, commentRequest))
                .expectErrorMatches(error ->
                        error instanceof ResponseStatusException &&
                                ((ResponseStatusException) error).getStatusCode() == HttpStatus.NOT_FOUND &&
                                Objects.equals(error.getMessage(), "404 NOT_FOUND \"Comment is not found\""))
                .verify();
    }

    @Test
    void deleteComment_Success() {
        // Mocking data
        String artworkId = "artworkId";
        String commentId = "commentId";

        ArtWork artwork = new ArtWork();
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setAuthorId("userId");
        comment.setAuthorName("John");
        artwork.setComments(new LinkedList<>(List.of(comment)));  // Make sure the collection is mutable

        User currentUser = new User();
        currentUser.setId("userId");
        currentUser.setFirstname("John");

        when(userService.getCurrentUser()).thenReturn(Mono.just(currentUser));
        when(artWorkService.findById(artworkId)).thenReturn(Mono.just(artwork));
        when(artWorkRepository.save(artwork)).thenReturn(Mono.just(artwork));

        // Test the delete method
        StepVerifier.create(commentService.delete(artworkId, commentId))
                .expectComplete()
                .verify();

        // Verify that the comment was deleted
        assertEquals(0, artwork.getComments().size());
    }

    @Test
    void deleteComment_Forbidden() {
        // Mocking data
        String artworkId = "artworkId";
        String commentId = "commentId";

        ArtWork artwork = new ArtWork();
        Comment comment = new Comment();
        comment.setId(commentId);
        comment.setAuthorId("anotherUserId");
        comment.setAuthorName("Jane");
        artwork.setComments(List.of(comment));

        User currentUser = new User();
        currentUser.setId("userId");
        currentUser.setFirstname("John");

        when(userService.getCurrentUser()).thenReturn(Mono.just(currentUser));
        when(artWorkService.findById(artworkId)).thenReturn(Mono.just(artwork));

        // Test the delete method and expect a Forbidden exception
        StepVerifier.create(commentService.delete(artworkId, commentId))
                .expectErrorMatches(
                        error -> {
                            if (error instanceof ResponseStatusException) {
                                ResponseStatusException exception = (ResponseStatusException) error;
                                return exception.getStatusCode().equals(HttpStatus.FORBIDDEN) &&
                                        exception.getReason().equals("You are not allowed to delete a comment for another user");
                            } else {
                                return false;
                            }
                        }
                )
                .verify();

        // Verify that the comment was not deleted
        assertEquals(1, artwork.getComments().size());
    }

    @Test
    void deleteComment_CommentNotFound() {
        // Mocking data
        String artworkId = "artworkId";
        String commentId = "nonExistentCommentId";

        ArtWork artwork = new ArtWork();

        User currentUser = new User();
        currentUser.setId("userId");
        currentUser.setFirstname("John");

        when(userService.getCurrentUser()).thenReturn(Mono.just(currentUser));
        when(artWorkService.findById(artworkId)).thenReturn(Mono.just(artwork));

        // Test the delete method and expect a Not Found exception
        StepVerifier.create(commentService.delete(artworkId, commentId))
                .verifyErrorMatches(
                        error -> {
                            if (error instanceof ResponseStatusException) {
                                ResponseStatusException exception = (ResponseStatusException) error;
                                return exception.getStatusCode().equals(HttpStatus.NOT_FOUND) &&
                                        exception.getReason().equals("Comment is not found");
                            } else {
                                return false;
                            }
                        }
                );
    }
}
