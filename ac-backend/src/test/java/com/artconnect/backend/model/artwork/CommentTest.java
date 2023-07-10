package com.artconnect.backend.model.artwork;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    @DisplayName("Test comment update")
    public void testCommentUpdate() {
        String id = "123";
        String authorId = "456";
        String authorName = "John Doe";
        Date createdAt = new Date();
        boolean isUpdated = false;
        Date updatedAt = null;
        String text = "This is a comment.";

        Comment comment = new Comment(id, authorId, authorName, createdAt, isUpdated, updatedAt, text);

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
