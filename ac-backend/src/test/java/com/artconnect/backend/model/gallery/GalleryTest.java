package com.artconnect.backend.model.gallery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.*;

public class GalleryTest {

    private Gallery gallery;
    private Gallery otherGallery;

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
        otherGallery = new Gallery();
    }

    @Test
    public void testNoArgsConstructor() {
        assertNotNull(otherGallery);
    }

    @Test
    public void testSettersAndGetters() {
        String id = "galleryId";
        String ownerId = "ownerId";
        String ownerName = "John Doe";
        String title = "Gallery Title";
        String description = "Gallery Description";
        List<String> artworkIds = Arrays.asList("artworkId1", "artworkId2");
        Set<GalleryCategory> categories = Collections.singleton(GalleryCategory.PAINTING);
        Map<String, Integer> evaluations = new HashMap<>();

        gallery.setId(id);
        gallery.setOwnerId(ownerId);
        gallery.setOwnerName(ownerName);
        gallery.setTitle(title);
        gallery.setDescription(description);
        gallery.setArtworkIds(artworkIds);
        gallery.setCategories(categories);
        gallery.setEvaluations(evaluations);

        assertEquals(id, gallery.getId());
        assertEquals(ownerId, gallery.getOwnerId());
        assertEquals(ownerName, gallery.getOwnerName());
        assertEquals(title, gallery.getTitle());
        assertEquals(description, gallery.getDescription());
        assertEquals(artworkIds, gallery.getArtworkIds());
        assertEquals(categories, gallery.getCategories());
        assertEquals(evaluations, gallery.getEvaluations());
    }

    @Test
    public void testEqualsAndHashCode() {
        Gallery gallery1 = new Gallery();
        Gallery gallery2 = new Gallery();
        Gallery gallery3 = new Gallery();

        // Two objects created using the default constructor should be equal and have the same hash code
        assertEquals(gallery1, gallery2);
        assertEquals(gallery1.hashCode(), gallery2.hashCode());

        // Modifying the field values should not affect equality
        gallery1.setId("id1");
        gallery2.setId("id2");
        gallery3.setId("id1");
        assertEquals(gallery1, gallery3);
        assertNotEquals(gallery1, gallery2);
        assertNotEquals(gallery2, gallery3);
        assertNotEquals(gallery1.hashCode(), gallery2.hashCode());
    }

    @Test
    public void testToString() {
        String id = "galleryId";
        String ownerId = "ownerId";
        String ownerName = "John Doe";
        String title = "Gallery Title";
        String description = "Gallery Description";
        List<String> artworkIds = Arrays.asList("artworkId1", "artworkId2");
        Set<GalleryCategory> categories = Collections.singleton(GalleryCategory.PAINTING);

        gallery.setId(id);
        gallery.setOwnerId(ownerId);
        gallery.setOwnerName(ownerName);
        gallery.setTitle(title);
        gallery.setDescription(description);
        gallery.setArtworkIds(artworkIds);
        gallery.setCategories(categories);

        String expectedToString = "Gallery(id=galleryId, ownerId=ownerId, ownerName=John Doe, title=Gallery Title, " +
                "description=Gallery Description, artworkIds=[artworkId1, artworkId2], categories=[PAINTING], " +
                "evaluations={})";

        assertEquals(expectedToString, gallery.toString());
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

    @Test
    public void testGalleryFields() {
        assertEquals("galleryId", gallery.getId());
        assertEquals("ownerId", gallery.getOwnerId());
        assertEquals("John Doe", gallery.getOwnerName());
        assertEquals("Gallery Title", gallery.getTitle());
        assertEquals("Gallery Description", gallery.getDescription());
        assertEquals(Arrays.asList("artworkId1", "artworkId2"), gallery.getArtworkIds());
        assertEquals(Collections.singleton(GalleryCategory.PAINTING), gallery.getCategories());
        assertNotNull(gallery.getEvaluations());
        assertTrue(gallery.getEvaluations().isEmpty());
    }
}