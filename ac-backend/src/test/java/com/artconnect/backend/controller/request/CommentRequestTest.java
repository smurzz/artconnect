package com.artconnect.backend.controller.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommentRequestTest {

    private CommentRequest commentRequest;

    @BeforeEach
    void setUp() {
        commentRequest = new CommentRequest();
    }

    @Test
    void setCommentText_ValidText_Success() {
        String validText = "This is a valid comment.";

        commentRequest.setCommentText(validText);

        assertEquals(validText, commentRequest.getCommentText());
    }

    @Test
    void builder_AllArgsConstructor_InitializedFields() {
        String commentText = "Test comment";

        CommentRequest commentRequest = CommentRequest.builder()
                .commentText(commentText)
                .build();

        assertNotNull(commentRequest);
        assertEquals(commentText, commentRequest.getCommentText());
    }

    @Test
    void builder_NoArgsConstructor_InitializedFields() {
        CommentRequest commentRequest = new CommentRequest();

        assertNotNull(commentRequest);
        // Ensure that the commentText field is initialized as null
        assertEquals(null, commentRequest.getCommentText());
    }
}
