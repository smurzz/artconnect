package com.artconnect.backend.model.artwork;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.artconnect.backend.model.Image;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertNotSame;


public class ArtWorkTest {
    @Test
    public void testCreateArtWork() {
        // Arrange
        String title = "Test Artwork";
        List<Image> images = new ArrayList<>();
        String description = "Test artwork description";
        int yearOfCreation = 2022;
        int likes = 10;
        List<String> materials = new ArrayList<>();
        Dimension dimension = new Dimension();
        double price = 99.99;
        List<String> tags = new ArrayList<>();
        String location = "Test location";
        Date createdAt = new Date();

        // Act
        ArtWork artwork = new ArtWork();
        artwork.setTitle(title);
        artwork.setImages(images);
        artwork.setDescription(description);
        artwork.setYearOfCreation(yearOfCreation);
        artwork.setLikes(likes);
        artwork.setMaterials(materials);
        artwork.setDimension(dimension);
        artwork.setPrice(price);
        artwork.setTags(tags);
        artwork.setLocation(location);
        artwork.setCreatedAt(createdAt);

        // Assert
        assertNotNull(artwork);
        assertEquals(title, artwork.getTitle());
        assertEquals(images, artwork.getImages());
        assertEquals(description, artwork.getDescription());
        assertEquals(yearOfCreation, artwork.getYearOfCreation());
        assertEquals(likes, artwork.getLikes());
        assertEquals(materials, artwork.getMaterials());
        assertEquals(dimension, artwork.getDimension());
        assertEquals(price, artwork.getPrice());
        assertEquals(tags, artwork.getTags());
        assertEquals(location, artwork.getLocation());
        assertEquals(createdAt, artwork.getCreatedAt());
    }

    @Test
    public void testUpdateArtworkTitle() {
        // Arrange
        ArtWork artwork = new ArtWork();
        String newTitle = "Updated Title";

        // Act
        artwork.setTitle(newTitle);

        // Assert
        assertEquals(newTitle, artwork.getTitle());
    }

    @Test
    public void testIncrementLikes() {
        // Arrange
        ArtWork artwork = new ArtWork();
        int initialLikes = artwork.getLikes();
        int expectedLikes = initialLikes + 1;

        // Act
        artwork.setLikes(artwork.getLikes() + 1);

        // Assert
        assertEquals(expectedLikes, artwork.getLikes());
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange
        String title = "Test Artwork";
        List<Image> images = new ArrayList<>();
        String description = "Test artwork description";
        int yearOfCreation = 2022;
        int likes = 10;
        List<String> materials = new ArrayList<>();
        Dimension dimension = new Dimension();
        double price = 99.99;
        List<String> tags = new ArrayList<>();
        String location = "Test location";
        Date createdAt = new Date();

        // Act
        ArtWork artwork = new ArtWork(title, images, description, yearOfCreation, likes, materials, dimension, price, tags, location, createdAt);

        // Assert
        assertNotNull(artwork);
        assertEquals(title, artwork.getTitle());
        assertEquals(images, artwork.getImages());
        assertEquals(description, artwork.getDescription());
        assertEquals(yearOfCreation, artwork.getYearOfCreation());
        assertEquals(likes, artwork.getLikes());
        assertEquals(materials, artwork.getMaterials());
        assertEquals(dimension, artwork.getDimension());
        assertEquals(price, artwork.getPrice());
        assertEquals(tags, artwork.getTags());
        assertEquals(location, artwork.getLocation());
        assertEquals(createdAt, artwork.getCreatedAt());
    }

    @Test
    public void testBuilder() {
        // Arrange
        String title = "Test Artwork";
        List<Image> images = new ArrayList<>();
        String description = "Test artwork description";
        int yearOfCreation = 2022;
        int likes = 10;
        List<String> materials = new ArrayList<>();
        Dimension dimension = new Dimension();
        double price = 99.99;
        List<String> tags = new ArrayList<>();
        String location = "Test location";
        Date createdAt = new Date();

        // Act
        ArtWork artwork = ArtWork.builder()
                .title(title)
                .images(images)
                .description(description)
                .yearOfCreation(yearOfCreation)
                .likes(likes)
                .materials(materials)
                .dimension(dimension)
                .price(price)
                .tags(tags)
                .location(location)
                .createdAt(createdAt)
                .build();

        // Assert
        assertNotNull(artwork);
        assertEquals(title, artwork.getTitle());
        assertEquals(images, artwork.getImages());
        assertEquals(description, artwork.getDescription());
        assertEquals(yearOfCreation, artwork.getYearOfCreation());
        assertEquals(likes, artwork.getLikes());
        assertEquals(materials, artwork.getMaterials());
        assertEquals(dimension, artwork.getDimension());
        assertEquals(price, artwork.getPrice());
        assertEquals(tags, artwork.getTags());
        assertEquals(location, artwork.getLocation());
        assertEquals(createdAt, artwork.getCreatedAt());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        ArtWork artwork1 = new ArtWork("Title", new ArrayList<>(), "Description", 2022, 10,
                new ArrayList<>(), new Dimension(), 99.99, new ArrayList<>(), "Location", new Date());
        ArtWork artwork2 = new ArtWork("Title", new ArrayList<>(), "Description", 2022, 10,
                new ArrayList<>(), new Dimension(), 99.99, new ArrayList<>(), "Location", new Date());

        // Assert
        assertEquals(artwork1, artwork2);
        assertEquals(artwork1.hashCode(), artwork2.hashCode());
    }

    @Test
    public void testToString() {
        // Arrange
        ArtWork artwork = new ArtWork();
        artwork.setTitle("Test Artwork");
        artwork.setImages(null); // Set images to null
        artwork.setDescription("Test artwork description");
        artwork.setYearOfCreation(2022);

        // Act
        String toStringResult = artwork.toString();

        // Assert
        String expectedString = "ArtWork(title=Test Artwork, images=null, description=Test artwork description, " +
                "yearOfCreation=2022, likes=0, materials=null, dimension=null, price=0.0, tags=null, " +
                "location=null, createdAt=null)";
        assertEquals(expectedString, toStringResult);
    }

    @Test
    public void testSetterGetter() {
        // Arrange
        ArtWork artwork = new ArtWork();
        String newTitle = "Updated Title";

        // Act
        artwork.setTitle(newTitle);
        String retrievedTitle = artwork.getTitle();

        // Assert
        assertEquals(newTitle, retrievedTitle);
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentTitle() {
        // Arrange
        ArtWork artwork1 = new ArtWork("Title1", new ArrayList<>(), "Description", 2022, 10,
                new ArrayList<>(), new Dimension(), 99.99, new ArrayList<>(), "Location", new Date());
        ArtWork artwork2 = new ArtWork("Title2", new ArrayList<>(), "Description", 2022, 10,
                new ArrayList<>(), new Dimension(), 99.99, new ArrayList<>(), "Location", new Date());

        // Assert
        assertNotEquals(artwork1, artwork2);
        assertNotEquals(artwork1.hashCode(), artwork2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentDescription() {
        // Arrange
        ArtWork artwork1 = new ArtWork("Title", new ArrayList<>(), "Description1", 2022, 10,
                new ArrayList<>(), new Dimension(), 99.99, new ArrayList<>(), "Location", new Date());
        ArtWork artwork2 = new ArtWork("Title", new ArrayList<>(), "Description2", 2022, 10,
                new ArrayList<>(), new Dimension(), 99.99, new ArrayList<>(), "Location", new Date());

        // Assert
        assertNotEquals(artwork1, artwork2);
        assertNotEquals(artwork1.hashCode(), artwork2.hashCode());
    }


    @Test
    public void testNoArgsConstructor() {
        // Arrange

        // Act
        ArtWork artwork = new ArtWork();

        // Assert
        assertNotNull(artwork);
    }

    @Test
    public void testAllArgsConstructorWithNoArguments() {
        // Arrange

        // Act
        ArtWork artwork = new ArtWork("", null, "", 0, 0, null, null, 0.0, null, null, null);

        // Assert
        assertNotNull(artwork);
    }

    @Test
    public void testSetterGetterForImages() {
        // Arrange
        ArtWork artwork = new ArtWork();
        List<Image> images = new ArrayList<>();

        // Act
        artwork.setImages(images);
        List<Image> retrievedImages = artwork.getImages();

        // Assert
        assertEquals(images, retrievedImages);
    }

    @Test
    public void testEqualsAndHashCodeWithNullFields() {
        // Arrange
        ArtWork artwork1 = new ArtWork(null, null, null, 0, 0, null, null, 0.0, null, null, null);
        ArtWork artwork2 = new ArtWork(null, null, null, 0, 0, null, null, 0.0, null, null, null);

        // Assert
        assertEquals(artwork1, artwork2);
        assertEquals(artwork1.hashCode(), artwork2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentFields() {
        // Arrange
        ArtWork artwork1 = new ArtWork("Title1", null, "Description1", 2022, 10, null, null, 99.99, null, "Location1", null);
        ArtWork artwork2 = new ArtWork("Title2", null, "Description2", 2023, 5, null, null, 49.99, null, "Location2", null);

        // Assert
        assertNotEquals(artwork1, artwork2);
        assertNotEquals(artwork1.hashCode(), artwork2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithSelf() {
        // Arrange
        ArtWork artwork = new ArtWork("Title", null, "Description", 2022, 10, null, null, 99.99, null, "Location", null);

        // Assert
        assertEquals(artwork, artwork);
        assertEquals(artwork.hashCode(), artwork.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentObject() {
        // Arrange
        ArtWork artwork = new ArtWork("Title", null, "Description", 2022, 10, null, null, 99.99, null, "Location", null);
        String differentObject = "Not an ArtWork object";

        // Assert
        assertNotEquals(artwork, differentObject);
        assertNotEquals(artwork.hashCode(), differentObject.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithEqualObjects() {
        // Arrange
        ArtWork artwork1 = new ArtWork("Title", null, "Description", 2022, 10, null, null, 99.99, null, "Location", null);
        ArtWork artwork2 = new ArtWork("Title", null, "Description", 2022, 10, null, null, 99.99, null, "Location", null);

        // Assert
        assertEquals(artwork1, artwork2);
        assertEquals(artwork1.hashCode(), artwork2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentLikes() {
        // Arrange
        ArtWork artwork1 = new ArtWork("Title", null, "Description", 2022, 10, null, null, 99.99, null, "Location", null);
        ArtWork artwork2 = new ArtWork("Title", null, "Description", 2022, 5, null, null, 99.99, null, "Location", null);

        // Assert
        assertNotEquals(artwork1, artwork2);
        assertNotEquals(artwork1.hashCode(), artwork2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentObjects() {
        // Arrange
        ArtWork artwork1 = new ArtWork();
        artwork1.setTitle("Title");
        artwork1.setImages(null);
        artwork1.setDescription("Description");
        artwork1.setYearOfCreation(2022);
        artwork1.setLikes(10);
        artwork1.setMaterials(null);
        artwork1.setDimension(null);
        artwork1.setPrice(99.99);
        artwork1.setTags(null);
        artwork1.setLocation("Location");
        artwork1.setCreatedAt(null);

        ArtWork artwork2 = new ArtWork();
        artwork2.setTitle("Title");
        artwork2.setImages(null);
        artwork2.setDescription("Description");
        artwork2.setYearOfCreation(2022);
        artwork2.setLikes(10);
        artwork2.setMaterials(null);
        artwork2.setDimension(null);
        artwork2.setPrice(99.99);
        artwork2.setTags(null);
        artwork2.setLocation("Location");
        artwork2.setCreatedAt(null);

        // Assert
        assertNotSame(artwork1, artwork2);
    }

    @Test
    public void testSetterGetterForTitle() {
        // Arrange
        ArtWork artwork = new ArtWork();
        String newTitle = "Updated Title";

        // Act
        artwork.setTitle(newTitle);
        String retrievedTitle = artwork.getTitle();

        // Assert
        assertEquals(newTitle, retrievedTitle);
    }


    @Test
    public void testSetterGetterForDescription() {
        // Arrange
        ArtWork artwork = new ArtWork();
        String description = "Test description";

        // Act
        artwork.setDescription(description);
        String retrievedDescription = artwork.getDescription();

        // Assert
        assertEquals(description, retrievedDescription);
    }

    @Test
    public void testSetterGetterForYearOfCreation() {
        // Arrange
        ArtWork artwork = new ArtWork();
        int yearOfCreation = 2022;

        // Act
        artwork.setYearOfCreation(yearOfCreation);
        int retrievedYearOfCreation = artwork.getYearOfCreation();

        // Assert
        assertEquals(yearOfCreation, retrievedYearOfCreation);
    }

    @Test
    public void testSetterGetterForLikes() {
        // Arrange
        ArtWork artwork = new ArtWork();
        int likes = 10;

        // Act
        artwork.setLikes(likes);
        int retrievedLikes = artwork.getLikes();

        // Assert
        assertEquals(likes, retrievedLikes);
    }

    @Test
    public void testSetterGetterForMaterials() {
        // Arrange
        ArtWork artwork = new ArtWork();
        List<String> materials = new ArrayList<>();

        // Act
        artwork.setMaterials(materials);
        List<String> retrievedMaterials = artwork.getMaterials();

        // Assert
        assertEquals(materials, retrievedMaterials);
    }

    @Test
    public void testSetterGetterForDimension() {
        // Arrange
        ArtWork artwork = new ArtWork();
        Dimension dimension = new Dimension();

        // Act
        artwork.setDimension(dimension);
        Dimension retrievedDimension = artwork.getDimension();

        // Assert
        assertEquals(dimension, retrievedDimension);
    }

    @Test
    public void testSetterGetterForPrice() {
        // Arrange
        ArtWork artwork = new ArtWork();
        double price = 99.99;

        // Act
        artwork.setPrice(price);
        double retrievedPrice = artwork.getPrice();

        // Assert
        assertEquals(price, retrievedPrice);
    }

    @Test
    public void testSetterGetterForTags() {
        // Arrange
        ArtWork artwork = new ArtWork();
        List<String> tags = new ArrayList<>();

        // Act
        artwork.setTags(tags);
        List<String> retrievedTags = artwork.getTags();

        // Assert
        assertEquals(tags, retrievedTags);
    }

    @Test
    public void testSetterGetterForLocation() {
        // Arrange
        ArtWork artwork = new ArtWork();
        String location = "Test location";

        // Act
        artwork.setLocation(location);
        String retrievedLocation = artwork.getLocation();

        // Assert
        assertEquals(location, retrievedLocation);
    }

    @Test
    public void testSetterGetterForCreatedAt() {
        // Arrange
        ArtWork artwork = new ArtWork();
        Date createdAt = new Date();

        // Act
        artwork.setCreatedAt(createdAt);
        Date retrievedCreatedAt = artwork.getCreatedAt();

        // Assert
        assertEquals(createdAt, retrievedCreatedAt);
    }

}