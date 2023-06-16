package com.artconnect.backend.model.artwork;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.artconnect.backend.model.Image;
import org.junit.jupiter.api.Test;

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

}