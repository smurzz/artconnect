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
                .dimension(new Dimension(100.0, 100.0, 50.0))
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

    @Test
    @DisplayName("Test getLikes method - Null likedByUsers")
    public void testGetLikes_NullLikedByUsers() {
        artWork.setLikedByUsers(null);

        assertEquals(0, artWork.getLikes());
    }

    @Test
    @DisplayName("Test isArtWorkLikedByUserId method - Null likedByUsers")
    public void testIsArtWorkLikedByUserId_NullLikedByUsers() {
        artWork.setLikedByUsers(null);
        String userEmail = "user@example.com";

        assertFalse(artWork.isArtWorkLikedByUserId(userEmail));
    }

    @Test
    @DisplayName("Test isArtWorkLikedByUserId method - Null userEmail")
    public void testIsArtWorkLikedByUserId_NullUserEmail() {
        String userEmail = null;

        assertFalse(artWork.isArtWorkLikedByUserId(userEmail));
    }

    @Test
    @DisplayName("Test isArtWorkLikedByUserId method - Empty userEmail")
    public void testIsArtWorkLikedByUserId_EmptyUserEmail() {
        String userEmail = "";

        assertFalse(artWork.isArtWorkLikedByUserId(userEmail));
    }

    @Test
    @DisplayName("Test isArtWorkLikedByUserId method - Null likedByUsers and userEmail")
    public void testIsArtWorkLikedByUserId_NullLikedByUsersAndUserEmail() {
        artWork.setLikedByUsers(null);
        String userEmail = null;

        assertFalse(artWork.isArtWorkLikedByUserId(userEmail));
    }

    @Test
    @DisplayName("Test isArtWorkLikedByUserId method - Null likedByUsers and empty userEmail")
    public void testIsArtWorkLikedByUserId_NullLikedByUsersAndEmptyUserEmail() {
        artWork.setLikedByUsers(null);
        String userEmail = "";

        assertFalse(artWork.isArtWorkLikedByUserId(userEmail));
    }

    @Test
    @DisplayName("Test isArtWorkLikedByUserId method - Empty likedByUsers")
    public void testIsArtWorkLikedByUserId_EmptyLikedByUsers() {
        artWork.setLikedByUsers(new HashSet<>());
        String userEmail = "user@example.com";

        assertFalse(artWork.isArtWorkLikedByUserId(userEmail));
    }

    @Test
    @DisplayName("Test isArtWorkLikedByUserId method - Invalid userEmail")
    public void testIsArtWorkLikedByUserId_InvalidUserEmail() {
        artWork.getLikedByUsers().add("user1@example.com");
        artWork.getLikedByUsers().add("user2@example.com");
        String userEmail = "invalid_email";

        assertFalse(artWork.isArtWorkLikedByUserId(userEmail));
    }

    @Test
    @DisplayName("Test isArtWorkLikedByUserId method - Valid userEmail")
    public void testIsArtWorkLikedByUserId_ValidUserEmail() {
        artWork.getLikedByUsers().add("user1@example.com");
        artWork.getLikedByUsers().add("user2@example.com");
        String userEmail = "user1@example.com";

        assertTrue(artWork.isArtWorkLikedByUserId(userEmail));
    }

    @Test
    @DisplayName("Test setLike method - User likes and unlikes artwork")
    public void testSetLike_UserLikesAndUnlikesArtwork() {
        String userEmail1 = "user1@example.com";
        String userEmail2 = "user2@example.com";

        artWork.setLike(userEmail1);
        assertTrue(artWork.getLikedByUsers().contains(userEmail1));
        assertFalse(artWork.getLikedByUsers().contains(userEmail2));

        artWork.setLike(userEmail2);
        assertTrue(artWork.getLikedByUsers().contains(userEmail1));
        assertTrue(artWork.getLikedByUsers().contains(userEmail2));

        artWork.setLike(userEmail1);
        assertFalse(artWork.getLikedByUsers().contains(userEmail1));
        assertTrue(artWork.getLikedByUsers().contains(userEmail2));

        artWork.setLike(userEmail1);
        assertTrue(artWork.getLikedByUsers().contains(userEmail1));
        assertTrue(artWork.getLikedByUsers().contains(userEmail2));

        artWork.setLike(userEmail2);
        assertTrue(artWork.getLikedByUsers().contains(userEmail1));
        assertFalse(artWork.getLikedByUsers().contains(userEmail2));

        artWork.setLike(userEmail1);
        assertFalse(artWork.getLikedByUsers().contains(userEmail1));
        assertFalse(artWork.getLikedByUsers().contains(userEmail2));
    }


    @Test
    @DisplayName("Test getLikes method - Empty likedByUsers")
    public void testGetLikes_EmptyLikedByUsers() {
        artWork.setLikedByUsers(new HashSet<>());

        assertEquals(0, artWork.getLikes());
    }

    @Test
    @DisplayName("Test isArtWorkLikedByUserId method - Artwork liked by user")
    public void testIsArtWorkLikedByUserId_ArtworkLikedByUser() {
        String userEmail = "user@example.com";
        artWork.getLikedByUsers().add(userEmail);

        assertTrue(artWork.isArtWorkLikedByUserId(userEmail));
    }

    @Test
    @DisplayName("Test isArtWorkLikedByUserId method - Artwork not liked by user")
    public void testIsArtWorkLikedByUserId_ArtworkNotLikedByUser() {
        String userEmail = "user@example.com";

        assertFalse(artWork.isArtWorkLikedByUserId(userEmail));
    }
}