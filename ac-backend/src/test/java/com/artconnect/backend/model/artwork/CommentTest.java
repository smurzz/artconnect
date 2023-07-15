package com.artconnect.backend.model.artwork;


import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommentTest {

    @Test
    @DisplayName("Test comment creation")
    public void testCommentCreation() {
        String id = "123";
        String authorId = "456";
        String authorName = "John Doe";
        Date createdAt = new Date();
        boolean isUpdated = false;
        Date updatedAt = null;
        String text = "This is a comment.";

        Comment comment = new Comment(id, authorId, authorName, createdAt, isUpdated, updatedAt, text);

        assertNotNull(comment);
        assertEquals(id, comment.getId());
        assertEquals(authorId, comment.getAuthorId());
        assertEquals(authorName, comment.getAuthorName());
        assertEquals(createdAt, comment.getCreatedAt());
        assertFalse(comment.isUpdated());
        assertEquals(updatedAt, comment.getUpdatedAt());
        assertEquals(text, comment.getText());
    }

    @Test
    @DisplayName("Test comment creation with builder")
    public void testCommentCreationWithBuilder() {
        String id = "123";
        String authorId = "456";
        String authorName = "John Doe";
        Date createdAt = new Date();
        boolean isUpdated = false;
        Date updatedAt = null;
        String text = "This is a comment.";

        Comment comment = Comment.builder()
                .id(id)
                .authorId(authorId)
                .authorName(authorName)
                .createdAt(createdAt)
                .isUpdated(isUpdated)
                .updatedAt(updatedAt)
                .text(text)
                .build();

        assertNotNull(comment);
        assertEquals(id, comment.getId());
        assertEquals(authorId, comment.getAuthorId());
        assertEquals(authorName, comment.getAuthorName());
        assertEquals(createdAt, comment.getCreatedAt());
        assertFalse(comment.isUpdated());
        assertEquals(updatedAt, comment.getUpdatedAt());
        assertEquals(text, comment.getText());
    }

    @Test
    @DisplayName("Test comment creation with no-args constructor")
    public void testCommentCreationWithNoArgsConstructor() {
        Comment comment = new Comment();

        assertNotNull(comment);
        assertNull(comment.getId());
        assertNull(comment.getAuthorId());
        assertNull(comment.getAuthorName());
        assertNull(comment.getCreatedAt());
        assertFalse(comment.isUpdated());
        assertNull(comment.getUpdatedAt());
        assertNull(comment.getText());
    }

    @Test
    @DisplayName("Test comment update")
    public void testCommentUpdate() {
        String id = "123";
        String authorId = "456";
        String authorName = "John Doe";
        Date createdAt = new Date();
        boolean isUpdated = false;
        Date updatedAt = null;
        String text = "This is a comment.";

        Comment comment = Comment.builder()
                .id(id)
                .authorId(authorId)
                .authorName(authorName)
                .createdAt(createdAt)
                .isUpdated(isUpdated)
                .updatedAt(updatedAt)
                .text(text)
                .build();

        Date newUpdatedAt = new Date();
        String newText = "Updated comment.";

        comment.setUpdated(true);
        comment.setUpdatedAt(newUpdatedAt);
        comment.setText(newText);

        assertTrue(comment.isUpdated());
        assertEquals(newUpdatedAt, comment.getUpdatedAt());
        assertEquals(newText, comment.getText());
    }
}
