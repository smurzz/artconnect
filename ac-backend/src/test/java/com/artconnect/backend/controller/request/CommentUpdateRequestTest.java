package com.artconnect.backend.controller.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class CommentUpdateRequestTest {

    @Test
    public void testCommentText() {
        CommentUpdateRequest request = new CommentUpdateRequest();
        request.setCommentText("testCommentText");
        assertEquals("testCommentText", request.getCommentText());
    }

    @Test
    public void testNoArgsConstructor() {
        CommentUpdateRequest request = new CommentUpdateRequest();
        assertNull(request.getCommentText());
    }
}
