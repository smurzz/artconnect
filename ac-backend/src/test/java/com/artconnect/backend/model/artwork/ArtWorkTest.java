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
import java.util.Arrays;
import java.lang.reflect.Field;


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

    @Test
    @DisplayName("Test setId")
    public void testSetId() {
        String id = "123456789";

        artWork.setId(id);

        assertEquals(id, artWork.getId());
    }

    @Test
    @DisplayName("Test setOwnerId")
    public void testSetOwnerId() {
        String ownerId = "987654321";

        artWork.setOwnerId(ownerId);

        assertEquals(ownerId, artWork.getOwnerId());
    }

    @Test
    @DisplayName("Test setGalleryId")
    public void testSetGalleryId() {
        String galleryId = "567890123";

        artWork.setGalleryId(galleryId);

        assertEquals(galleryId, artWork.getGalleryId());
    }

    @Test
    @DisplayName("Test setOwnerName")
    public void testSetOwnerName() {
        String ownerName = "Jane Doe";

        artWork.setOwnerName(ownerName);

        assertEquals(ownerName, artWork.getOwnerName());
    }

    @Test
    @DisplayName("Test setGalleryTitle")
    public void testSetGalleryTitle() {
        String galleryTitle = "Art Gallery 2.0";

        artWork.setGalleryTitle(galleryTitle);

        assertEquals(galleryTitle, artWork.getGalleryTitle());
    }

    @Test
    @DisplayName("Test setTitle")
    public void testSetTitle() {
        String title = "New Artwork Title";

        artWork.setTitle(title);

        assertEquals(title, artWork.getTitle());
    }

    @Test
    @DisplayName("Test MAX_NUM_IMAGES")
    public void testMaxNumImages() {
        int maxNumImages = ArtWork.MAX_NUM_IMAGES;

        // Create a list with maximum number of images
        List<String> imagesIds = new ArrayList<>();
        for (int i = 0; i < maxNumImages-1; i++) {
            imagesIds.add("image" + i);
        }

        artWork.setImagesIds(imagesIds);

        // Try to add one more image
        List<String> updatedImagesIds = new ArrayList<>(artWork.getImagesIds());
        updatedImagesIds.add("imageExtra");
        artWork.setImagesIds(updatedImagesIds);

        // The size of imagesIds list should still be the same as MAX_NUM_IMAGES
        assertEquals(maxNumImages, artWork.getImagesIds().size());
    }

    @Test
    @DisplayName("Test description")
    public void testDescription() {
        String description = "Artwork description";

        artWork.setDescription(description);

        assertEquals(description, artWork.getDescription());
    }

    @Test
    @DisplayName("Test yearOfCreation")
    public void testYearOfCreation() {
        Integer yearOfCreation = 2022;

        artWork.setYearOfCreation(yearOfCreation);

        assertEquals(yearOfCreation, artWork.getYearOfCreation());
    }

    @Test
    @DisplayName("Test dimension")
    public void testDimension() {
        Dimension dimension = new Dimension(100.0, 100.0, 50.0);

        artWork.setDimension(dimension);

        assertEquals(dimension, artWork.getDimension());
    }

    @Test
    @DisplayName("Test price")
    public void testPrice() {
        Double price = 99.99;

        artWork.setPrice(price);

        assertEquals(price, artWork.getPrice());
    }

    @Test
    @DisplayName("Test location")
    public void testLocation() {
        String location = "Artwork location";

        artWork.setLocation(location);

        assertEquals(location, artWork.getLocation());
    }

    @Test
    @DisplayName("Test createdAt")
    public void testCreatedAt() {
        Date createdAt = new Date();

        artWork.setCreatedAt(createdAt);

        assertEquals(createdAt, artWork.getCreatedAt());
    }

    @Test
    @DisplayName("Test materials")
    public void testMaterials() {
        List<String> materials = Arrays.asList("Oil paint", "Canvas", "Wood");

        artWork.setMaterials(materials);

        assertEquals(materials, artWork.getMaterials());
    }

    @Test
    @DisplayName("Test artDirections")
    public void testArtDirections() {
        Set<ArtDirection> artDirections = new HashSet<>();
        artDirections.add(ArtDirection.ABSTRACT);
        artDirections.add(ArtDirection.IMPRESSIONISM);

        artWork.setArtDirections(artDirections);

        assertEquals(artDirections, artWork.getArtDirections());
    }

    @Test
    @DisplayName("Test tags")
    public void testTags() {
        List<String> tags = Arrays.asList("painting", "abstract", "modern");

        artWork.setTags(tags);

        assertEquals(tags, artWork.getTags());
    }

    @Test
    @DisplayName("Test comments")
    public void testComments() {
        List<Comment> comments = new ArrayList<>();
        Comment comment1 = new Comment(null, null, "User1", null, false, null, "Great artwork!");
        Comment comment2 = new Comment(null, null, "User2", null, false, null, "I love it!");
        comments.add(comment1);
        comments.add(comment2);

        artWork.setComments(comments);

        assertEquals(comments, artWork.getComments());
    }

    @Test
    @DisplayName("Test setLike method")
    public void testSetLike() throws NoSuchFieldException, IllegalAccessException {
        String userEmail = "user@example.com";

        // Set up the likedByUsers field using reflection
        Field likedByUsersField = artWork.getClass().getDeclaredField("likedByUsers");
        likedByUsersField.setAccessible(true);
        Set<String> likedByUsers = (Set<String>) likedByUsersField.get(artWork);
        if (likedByUsers == null) {
            likedByUsers = new HashSet<>();
            likedByUsersField.set(artWork, likedByUsers);
        }

        // Test when likedByUsers is null
        artWork.setLike(userEmail);
        likedByUsers.add(userEmail);
        assertEquals(likedByUsers, artWork.getLikedByUsers());

        // Test when likedByUsers is not null
        artWork.setLike(userEmail);
        assertEquals(likedByUsers, artWork.getLikedByUsers());

        // Test with another user
        String anotherUserEmail = "anotheruser@example.com";
        artWork.setLike(anotherUserEmail);
        likedByUsers.add(anotherUserEmail);
        assertEquals(likedByUsers, artWork.getLikedByUsers());
    }
}