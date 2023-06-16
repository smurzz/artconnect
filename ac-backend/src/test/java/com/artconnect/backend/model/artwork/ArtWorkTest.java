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

}