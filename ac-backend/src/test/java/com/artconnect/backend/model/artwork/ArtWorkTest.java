package com.artconnect.backend.model.artwork;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArtWorkTest {

    private ArtWork artWork;

    @BeforeEach
    public void setUp() {
        artWork = ArtWork.builder()
                .id("123")
                .ownerId("456")
                .galleryId("789")
                .ownerName("John Doe")
                .galleryTitle("Art Gallery")
                .title("Artwork Title")
                .description("This is an artwork.")
                .yearOfCreation(2021)
                .price(1000.0)
                .location("New York")
                .createdAt(new Date())
                .imagesIds(new ArrayList<>())
                .likedByUsers(new HashSet<>())
                .materials(new ArrayList<>())
                .artDirections(new HashSet<>())
                .tags(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("Test setLike method - User likes artwork")
    public void testSetLike_UserLikesArtwork() {
        String userEmail = "test@example.com";

        artWork.setLike(userEmail);

        assertTrue(artWork.getLikedByUsers().contains(userEmail));
    }

    @Test
    @DisplayName("Test setLike method - User unlikes artwork")
    public void testSetLike_UserUnlikesArtwork() {
        String userEmail = "test@example.com";
        artWork.getLikedByUsers().add(userEmail);

        artWork.setLike(userEmail);

        assertFalse(artWork.getLikedByUsers().contains(userEmail));
    }

    @Test
    @DisplayName("Test getLikes method - No likes")
    public void testGetLikes_NoLikes() {
        assertEquals(0, artWork.getLikes());
    }

    @Test
    @DisplayName("Test getLikes method - With likes")
    public void testGetLikes_WithLikes() {
        artWork.getLikedByUsers().add("user1@example.com");
        artWork.getLikedByUsers().add("user2@example.com");

        assertEquals(2, artWork.getLikes());
    }

    @Test
    @DisplayName("Test isArtWorkLikedByUserId method - Artwork not liked by user")
    public void testIsArtWorkLikedByUserId_NotLikedByUser() {
        String userEmail = "user@example.com";

        assertFalse(artWork.isArtWorkLikedByUserId(userEmail));
    }

    @Test
    @DisplayName("Test isArtWorkLikedByUserId method - Artwork liked by user")
    public void testIsArtWorkLikedByUserId_LikedByUser() {
        String userEmail = "user@example.com";
        artWork.getLikedByUsers().add(userEmail);

        assertTrue(artWork.isArtWorkLikedByUserId(userEmail));
    }
}