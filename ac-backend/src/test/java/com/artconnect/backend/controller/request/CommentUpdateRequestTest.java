package com.artconnect.backend.controller.request;

import com.artconnect.backend.validation.NotBlankIfSpecified;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class CommentUpdateRequestTest {

    @Test
    void commentTextShouldBeNullByDefault() {
        // Arrange
        CommentUpdateRequest request = new CommentUpdateRequest();

        // Act
        String commentText = request.getCommentText();

        // Assert
        assertNull(commentText);
    }

    @Test
    void commentTextShouldBeSetCorrectly() {
        // Arrange
        String expectedCommentText = "Test Comment";
        CommentUpdateRequest request = CommentUpdateRequest.builder()
                .commentText(expectedCommentText)
                .build();

        // Act
        String commentText = request.getCommentText();

        // Assert
        assertEquals(expectedCommentText, commentText);
    }

    @Test
    void commentTextShouldNotBeBlankIfSpecified() {
        // Arrange
        CommentUpdateRequest request = CommentUpdateRequest.builder()
                .commentText("")
                .build();

        // Act
        boolean isValid = request.getClass().isAnnotationPresent(NotBlankIfSpecified.class);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void commentTextShouldBeNotBlankIfSpecified() throws NoSuchFieldException {
        // Arrange
        CommentUpdateRequest request = CommentUpdateRequest.builder()
                .commentText("Test Comment")
                .build();

        // Act
        Field commentTextField = CommentUpdateRequest.class.getDeclaredField("commentText");
        NotBlankIfSpecified annotation = commentTextField.getAnnotation(NotBlankIfSpecified.class);

        // Assert
        assertNotNull(annotation);
    }

    @Test
    void builderShouldCreateNonNullObject() {
        // Act
        CommentUpdateRequest request = CommentUpdateRequest.builder().build();

        // Assert
        assertNotNull(request);
    }

    @Test
    void allArgsConstructorShouldSetFieldsCorrectly() {
        // Arrange
        String expectedCommentText = "Test Comment";

        // Act
        CommentUpdateRequest request = new CommentUpdateRequest(expectedCommentText);

        // Assert
        assertEquals(expectedCommentText, request.getCommentText());
    }
}
