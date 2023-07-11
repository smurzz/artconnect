package com.artconnect.backend.controller.request;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class CommentRequestTest {
//
//    private CommentRequest commentRequest;
//
//    @BeforeEach
//    public void setUp() {
//        commentRequest = new CommentRequest();
//    }
//
//    @Test
//    public void testCommentTextNotBlank() {
//        String commentText = "This is a comment";
//        commentRequest.setCommentText(commentText);
//        assertEquals(commentText, commentRequest.getCommentText());
//
//        try {
//            commentRequest.setCommentText(null);
//            assertEquals(true, false);
//        } catch (javax.validation.ConstraintViolationException e) {
//            assertEquals(true, true);
//        }
//
//        try {
//            commentRequest.setCommentText("");
//            assertEquals(true, false);
//        } catch (javax.validation.ConstraintViolationException e) {
//            assertEquals(true, true);
//        }
//
//        try {
//            commentRequest.setCommentText(" ");
//            assertEquals(true, false);
//        } catch (javax.validation.ConstraintViolationException e) {
//            assertEquals(true, true);
//        }
//    }
//}

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class CommentRequestTest {

    @Test
    public void testCommentText() {
        CommentRequest request = new CommentRequest();
        request.setCommentText("testCommentText");
        assertEquals("testCommentText", request.getCommentText());
    }

    @Test
    public void testNoArgsConstructor() {
        CommentRequest request = new CommentRequest();
        assertNull(request.getCommentText());
    }
}

