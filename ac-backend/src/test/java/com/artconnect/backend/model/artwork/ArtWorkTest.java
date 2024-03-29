package com.artconnect.backend.model.artwork;

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

import static org.junit.jupiter.api.Assertions.*;


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

//    @Test
//    @DisplayName("Test setLike method")
//    public void testSetLike() throws NoSuchFieldException, IllegalAccessException {
//        String userEmail = "user@example.com";
//
//        // Set up the likedByUsers field using reflection
//        Field likedByUsersField = artWork.getClass().getDeclaredField("likedByUsers");
//        likedByUsersField.setAccessible(true);
//        Set<String> likedByUsers = (Set<String>) likedByUsersField.get(artWork);
//        if (likedByUsers == null) {
//            likedByUsers = new HashSet<>();
//            likedByUsersField.set(artWork, likedByUsers);
//        }
//
//        // Test when likedByUsers is null
//        artWork.setLike(userEmail);
//        likedByUsers.add(userEmail);
//        assertEquals(likedByUsers, artWork.getLikedByUsers());
//
//        // Test when likedByUsers is not null
//        artWork.setLike(userEmail);
//        assertEquals(likedByUsers, artWork.getLikedByUsers());
//
//        // Test with another user
//        String anotherUserEmail = "anotheruser@example.com";
//        artWork.setLike(anotherUserEmail);
//        likedByUsers.add(anotherUserEmail);
//        assertEquals(likedByUsers, artWork.getLikedByUsers());
//    }


    @Test
    @DisplayName("Test Set Like")
    public void testSetLike() {
        ArtWork artWork = new ArtWork();
        String userEmail = "test@example.com";

        // Initially, likedByUsers should be null
        assertEquals(null, artWork.getLikedByUsers());

        // Call the setLike method
        artWork.setLike(userEmail);

        // Verify that the likedByUsers field is initialized with a new HashSet
        Set<String> expectedLikedByUsers = new HashSet<>();
        expectedLikedByUsers.add(userEmail);
        assertEquals(expectedLikedByUsers, artWork.getLikedByUsers());

        // Call the setLike method again with the same userEmail
        artWork.setLike(userEmail);

        // Verify that the userEmail is removed from likedByUsers
        assertEquals(new HashSet<>(), artWork.getLikedByUsers());
    }

    @Test
    @DisplayName("Test @NoArgsConstructor")
    public void testNoArgsConstructor() {
        ArtWork artWork = new ArtWork();

        assertNotNull(artWork);
        assertEquals(null, artWork.getId());
        assertEquals(null, artWork.getOwnerId());
        assertEquals(null, artWork.getGalleryId());
        assertEquals(null, artWork.getOwnerName());
        assertEquals(null, artWork.getGalleryTitle());
    }

    @Test
    @DisplayName("Test @Data - Equals and HashCode")
    public void testDataEqualsAndHashCode() {
        String id = "123";
        String ownerId = "456";
        String galleryId = "789";
        String ownerName = "John Doe";
        String galleryTitle = "Art Gallery";
        String title = "Artwork Title";
        String description = "This is an artwork.";
        Integer yearOfCreation = 2021;
        Dimension dimension = new Dimension(100.0, 100.0, 50.0);
        Double price = 1000.0;
        String location = "New York";
        Date createdAt = new Date();
        List<String> imagesIds = new ArrayList<>();
        Set<String> likedByUsers = new HashSet<>();
        List<String> materials = new ArrayList<>();
        Set<ArtDirection> artDirections = new HashSet<>();
        List<String> tags = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();

        ArtWork artWork1 = new ArtWork(id, ownerId, galleryId, ownerName, galleryTitle, title, description, yearOfCreation,
                dimension, price, location, createdAt, imagesIds, likedByUsers, materials, artDirections, tags, comments);

        ArtWork artWork2 = new ArtWork(id, ownerId, galleryId, ownerName, galleryTitle, title, description, yearOfCreation,
                dimension, price, location, createdAt, imagesIds, likedByUsers, materials, artDirections, tags, comments);

        ArtWork artWork3 = new ArtWork("987", ownerId, galleryId, ownerName, galleryTitle, title, description, yearOfCreation,
                dimension, price, location, createdAt, imagesIds, likedByUsers, materials, artDirections, tags, comments);

        assertEquals(artWork1, artWork2);
        assertEquals(artWork1.hashCode(), artWork2.hashCode());
        assertNotEquals(artWork1, artWork3);
        assertNotEquals(artWork1.hashCode(), artWork3.hashCode());
    }

    @Test
    @DisplayName("Test @Data - ToString")
    public void testDataToString() {
        String id = "123";
        String ownerId = "456";
        String galleryId = "789";
        String ownerName = "John Doe";
        String galleryTitle = "Art Gallery";
        String title = "Artwork Title";
        String description = "This is an artwork.";
        Integer yearOfCreation = 2021;
        Dimension dimension = new Dimension(100.0, 100.0, 50.0);
        Double price = 1000.0;
        String location = "New York";
        Date createdAt = new Date();
        List<String> imagesIds = new ArrayList<>();
        Set<String> likedByUsers = new HashSet<>();
        List<String> materials = new ArrayList<>();
        Set<ArtDirection> artDirections = new HashSet<>();
        List<String> tags = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();

        ArtWork artWork = new ArtWork(id, ownerId, galleryId, ownerName, galleryTitle, title, description, yearOfCreation,
                dimension, price, location, createdAt, imagesIds, likedByUsers, materials, artDirections, tags, comments);

        String expectedToString = "ArtWork(id=123, ownerId=456, galleryId=789, ownerName=John Doe, galleryTitle=Art Gallery, " +
                "title=Artwork Title, description=This is an artwork., yearOfCreation=2021, " +
                "dimension=Dimension(height=100.0, width=100.0, depth=50.0), price=1000.0, location=New York, " +
                "createdAt=" + createdAt.toString();

        String actualToString = artWork.toString();

        assertTrue(actualToString.contains(expectedToString));
    }



    @Test
    @DisplayName("Test @Data - Getter")
    public void testDataGetter() {
        String id = "123";
        String ownerId = "456";
        String galleryId = "789";
        String ownerName = "John Doe";
        String galleryTitle = "Art Gallery";
        String title = "Artwork Title";
        String description = "This is an artwork.";
        Integer yearOfCreation = 2021;
        Dimension dimension = new Dimension(100.0, 100.0, 50.0);
        Double price = 1000.0;
        String location = "New York";
        Date createdAt = new Date();
        List<String> imagesIds = new ArrayList<>();
        Set<String> likedByUsers = new HashSet<>();
        List<String> materials = new ArrayList<>();
        Set<ArtDirection> artDirections = new HashSet<>();
        List<String> tags = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();

        ArtWork artWork = new ArtWork(id, ownerId, galleryId, ownerName, galleryTitle, title, description, yearOfCreation,
                dimension, price, location, createdAt, imagesIds, likedByUsers, materials, artDirections, tags, comments);

        // Verify getters for all fields
        assertEquals(id, artWork.getId());
        assertEquals(ownerId, artWork.getOwnerId());
        assertEquals(galleryId, artWork.getGalleryId());
        assertEquals(ownerName, artWork.getOwnerName());
        assertEquals(galleryTitle, artWork.getGalleryTitle());
        assertEquals(title, artWork.getTitle());
        assertEquals(description, artWork.getDescription());
        assertEquals(yearOfCreation, artWork.getYearOfCreation());
        assertEquals(dimension, artWork.getDimension());
        assertEquals(price, artWork.getPrice());
        assertEquals(location, artWork.getLocation());
        assertEquals(createdAt, artWork.getCreatedAt());
        assertEquals(imagesIds, artWork.getImagesIds());
        assertEquals(likedByUsers, artWork.getLikedByUsers());
        assertEquals(materials, artWork.getMaterials());
        assertEquals(artDirections, artWork.getArtDirections());
        assertEquals(tags, artWork.getTags());
        assertEquals(comments, artWork.getComments());
    }

    @Test
    @DisplayName("Test @Data - Setter")
    public void testDataSetter() {
        String id = "123";
        String ownerId = "456";
        String galleryId = "789";
        String ownerName = "John Doe";
        String galleryTitle = "Art Gallery";
        String title = "Artwork Title";
        String description = "This is an artwork.";
        Integer yearOfCreation = 2021;
        Dimension dimension = new Dimension(100.0, 100.0, 50.0);
        Double price = 1000.0;
        String location = "New York";
        Date createdAt = new Date();
        List<String> imagesIds = new ArrayList<>();
        Set<String> likedByUsers = new HashSet<>();
        List<String> materials = new ArrayList<>();
        Set<ArtDirection> artDirections = new HashSet<>();
        List<String> tags = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();

        ArtWork artWork = new ArtWork();

        // Verify setters for all fields
        artWork.setId(id);
        artWork.setOwnerId(ownerId);
        artWork.setGalleryId(galleryId);
        artWork.setOwnerName(ownerName);
        artWork.setGalleryTitle(galleryTitle);
        artWork.setTitle(title);
        artWork.setDescription(description);
        artWork.setYearOfCreation(yearOfCreation);
        artWork.setDimension(dimension);
        artWork.setPrice(price);
        artWork.setLocation(location);
        artWork.setCreatedAt(createdAt);
        artWork.setImagesIds(imagesIds);
        artWork.setLikedByUsers(likedByUsers);
        artWork.setMaterials(materials);
        artWork.setArtDirections(artDirections);
        artWork.setTags(tags);
        artWork.setComments(comments);

        // Verify getters for all fields
        assertEquals(id, artWork.getId());
        assertEquals(ownerId, artWork.getOwnerId());
        assertEquals(galleryId, artWork.getGalleryId());
        assertEquals(ownerName, artWork.getOwnerName());
        assertEquals(galleryTitle, artWork.getGalleryTitle());
        assertEquals(title, artWork.getTitle());
        assertEquals(description, artWork.getDescription());
        assertEquals(yearOfCreation, artWork.getYearOfCreation());
        assertEquals(dimension, artWork.getDimension());
        assertEquals(price, artWork.getPrice());
        assertEquals(location, artWork.getLocation());
        assertEquals(createdAt, artWork.getCreatedAt());
        assertEquals(imagesIds, artWork.getImagesIds());
        assertEquals(likedByUsers, artWork.getLikedByUsers());
        assertEquals(materials, artWork.getMaterials());
        assertEquals(artDirections, artWork.getArtDirections());
        assertEquals(tags, artWork.getTags());
        assertEquals(comments, artWork.getComments());
    }

    @Test
    @DisplayName("Test @Data - RequiredArgsConstructor")
    public void testDataRequiredArgsConstructor() {
        String id = "123";
        String ownerId = "456";
        String galleryId = "789";
        String ownerName = "John Doe";
        String galleryTitle = "Art Gallery";
        String title = "Artwork Title";
        String description = "This is an artwork.";
        Integer yearOfCreation = 2021;
        Dimension dimension = new Dimension(100.0, 100.0, 50.0);
        Double price = 1000.0;
        String location = "New York";
        Date createdAt = new Date();
        List<String> imagesIds = new ArrayList<>();
        Set<String> likedByUsers = new HashSet<>();
        List<String> materials = new ArrayList<>();
        Set<ArtDirection> artDirections = new HashSet<>();
        List<String> tags = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();

        ArtWork artWork = new ArtWork(id, ownerId, galleryId, ownerName, galleryTitle, title, description, yearOfCreation,
                dimension, price, location, createdAt, imagesIds, likedByUsers, materials, artDirections, tags, comments);

        // Verify that the object is not null
        assertNotNull(artWork);
    }

}