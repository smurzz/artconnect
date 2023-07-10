package com.artconnect.backend.model.gallery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GalleryTest {

    private Gallery gallery;

    @BeforeEach
    public void setUp() {
        gallery = Gallery.builder()
                .id("galleryId")
                .ownerId("ownerId")
                .ownerName("John Doe")
                .title("Gallery Title")
                .description("Gallery Description")
                .artworkIds(Arrays.asList("artworkId1", "artworkId2"))
                .categories(Collections.singleton(GalleryCategory.PAINTING))
                .evaluations(new HashMap<>())
                .build();
    }

    @Test
    public void testGetRating_NoEvaluations_ReturnsZero() {
        double rating = gallery.getRating();
        assertEquals(0.0, rating);
    }

    @Test
    public void testGetRating_WithEvaluations_ReturnsAverageRating() {
        Map<String, Integer> evaluations = new HashMap<>();
        evaluations.put("user1", 4);
        evaluations.put("user2", 3);
        evaluations.put("user3", 5);
        gallery.setEvaluations(evaluations);

        double rating = gallery.getRating();
        assertEquals(4.0, rating);
    }

    @Test
    public void testSetEvaluation_AddEvaluation() {
        gallery.setEvaluation("user1", 4);

        Map<String, Integer> evaluations = gallery.getEvaluations();
        assertNotNull(evaluations);
        assertEquals(1, evaluations.size());
        assertTrue(evaluations.containsKey("user1"));
        assertEquals(4, evaluations.get("user1"));
    }

    @Test
    public void testGetRating_NullEvaluations_ReturnsZero() {
        gallery.setEvaluations(null);
        double rating = gallery.getRating();
        assertEquals(0.0, rating);
    }

    @Test
    public void testSetEvaluation_OverrideExistingEvaluation() {
        Map<String, Integer> evaluations = new HashMap<>();
        evaluations.put("user1", 4);
        gallery.setEvaluations(evaluations);

        gallery.setEvaluation("user1", 5);

        evaluations = gallery.getEvaluations();
        assertNotNull(evaluations);
        assertEquals(1, evaluations.size());
        assertTrue(evaluations.containsKey("user1"));
        assertEquals(5, evaluations.get("user1"));
    }

    @Test
    public void testSetEvaluation_AddMultipleEvaluations() {
        gallery.setEvaluation("user1", 4);
        gallery.setEvaluation("user2", 3);
        gallery.setEvaluation("user3", 5);

        Map<String, Integer> evaluations = gallery.getEvaluations();
        assertNotNull(evaluations);
        assertEquals(3, evaluations.size());
        assertTrue(evaluations.containsKey("user1"));
        assertTrue(evaluations.containsKey("user2"));
        assertTrue(evaluations.containsKey("user3"));
        assertEquals(4, evaluations.get("user1"));
        assertEquals(3, evaluations.get("user2"));
        assertEquals(5, evaluations.get("user3"));
    }

    @Test
    public void testSetEvaluation_NullEvaluations_CreateNewMapAndAddEvaluation() {
        gallery.setEvaluations(null);

        gallery.setEvaluation("user1", 4);

        Map<String, Integer> evaluations = gallery.getEvaluations();
        assertNotNull(evaluations);
        assertEquals(1, evaluations.size());
        assertTrue(evaluations.containsKey("user1"));
        assertEquals(4, evaluations.get("user1"));
    }

    @Test
    public void testGetRating_EmptyEvaluations_ReturnsZero() {
        gallery.setEvaluations(new HashMap<>());

        double rating = gallery.getRating();

        assertEquals(0.0, rating);
    }

    @Test
    public void testGetRating_RoundingRating_ReturnsRoundedValue() {
        Map<String, Integer> evaluations = new HashMap<>();
        evaluations.put("user1", 3);
        evaluations.put("user2", 4);
        evaluations.put("user3", 5);
        gallery.setEvaluations(evaluations);

        double rating = gallery.getRating();

        assertEquals(4.0, rating);
    }

    @Test
    public void testSetEvaluation_NullUserIdAndRating_DoNotAddEvaluation() {
        gallery.setEvaluation(null, null);

        Map<String, Integer> evaluations = gallery.getEvaluations();

        assertEquals(1, evaluations.size());
        assertNull(evaluations.get(null));
    }

    @Test
    public void testSetEvaluation_NullUserId_AddEvaluationWithNullUserId() {
        gallery.setEvaluation(null, 4);

        Map<String, Integer> evaluations = gallery.getEvaluations();

        assertNotNull(evaluations);
        assertEquals(1, evaluations.size());
        assertTrue(evaluations.containsKey(null));
        assertEquals(4, evaluations.get(null));
    }

    @Test
    public void testSetEvaluation_NullRating_AddEvaluationWithNullRating() {
        gallery.setEvaluation("user1", null);

        Map<String, Integer> evaluations = gallery.getEvaluations();

        assertNotNull(evaluations);
        assertEquals(1, evaluations.size());
        assertTrue(evaluations.containsKey("user1"));
        assertNull(evaluations.get("user1"));
    }

    @Test
    public void testSetEvaluation_NullEvaluations_AddEvaluationWithNullEvaluations() {
        gallery.setEvaluations(null);
        gallery.setEvaluation("user1", 4);

        Map<String, Integer> evaluations = gallery.getEvaluations();

        assertNotNull(evaluations);
        assertEquals(1, evaluations.size());
        assertTrue(evaluations.containsKey("user1"));
        assertEquals(4, evaluations.get("user1"));
    }

    @Test
    public void testGetRating_NegativeRatings_ReturnsAverageRating() {
        Map<String, Integer> evaluations = new HashMap<>();
        evaluations.put("user1", -2);
        evaluations.put("user2", 3);
        evaluations.put("user3", 5);
        gallery.setEvaluations(evaluations);

        double rating = gallery.getRating();

        assertEquals(2.0, rating);
    }

    @Test
    public void testSetEvaluation_RemoveExistingEvaluation() {
        Map<String, Integer> evaluations = new HashMap<>();
        evaluations.put("user1", 4);
        gallery.setEvaluations(evaluations);

        gallery.setEvaluation("user1", null);

        evaluations = gallery.getEvaluations();
        assertNotNull(evaluations);
        assertNull(evaluations.get("user1")); // Verify that the value is null
        assertEquals(1, evaluations.size());
    }
}