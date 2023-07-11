package com.artconnect.backend.controller.request;

import com.artconnect.backend.controller.request.ArtWorkRequest;
import com.artconnect.backend.model.artwork.ArtDirection;
import com.artconnect.backend.model.artwork.Dimension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class ArtWorkRequestTest {
    @Mock
    private Dimension dimension;

    @InjectMocks
    private ArtWorkRequest artWorkRequest;

    @BeforeEach
    void setUp() {
        artWorkRequest = new ArtWorkRequest();
    }

    @Test
    void setTitle_ValidInput_Success() {
        // Arrange
        String title = "Test Title";

        // Act
        artWorkRequest.setTitle(title);

        // Assert
        assertEquals(title, artWorkRequest.getTitle());
    }

    @Test
    void setYearOfCreation_ValidInput_Success() {
        Integer year = 2022;
        artWorkRequest.setYearOfCreation(year);
        assertEquals(year, artWorkRequest.getYearOfCreation());
    }


    @Test
    void setMaterials_ValidInput_Success() {
        List<String> materials = Arrays.asList("Oil paint", "Canvas");
        artWorkRequest.setMaterials(materials);
        assertEquals(materials, artWorkRequest.getMaterials());
    }


    @Test
    void setPrice_ValidInput_Success() {
        Double price = 100.0;
        artWorkRequest.setPrice(price);
        assertEquals(price, artWorkRequest.getPrice());
    }

    @Test
    void setTags_ValidInput_Success() {
        List<String> tags = Arrays.asList("Abstract", "Painting");
        artWorkRequest.setTags(tags);
        assertEquals(tags, artWorkRequest.getTags());
    }

    @Test
    void setDimension_ValidInput_Success() {
        // Arrange
        Double height = 10.0;
        Double width = 20.0;
        Double depth = 5.0;

        // Mock the Dimension object
        when(dimension.getHeight()).thenReturn(height);
        when(dimension.getWidth()).thenReturn(width);
        when(dimension.getDepth()).thenReturn(depth);

        // Act
        artWorkRequest.setDimension(dimension);

        // Assert
        assertEquals(height, artWorkRequest.getDimension().getHeight());
        assertEquals(width, artWorkRequest.getDimension().getWidth());
        assertEquals(depth, artWorkRequest.getDimension().getDepth());
    }

    @Test
    void setArtDirections_ValidInput_Success() {
        // Arrange
        Set<ArtDirection> artDirections = new HashSet<>(Arrays.asList(
                ArtDirection.ABSTRACT, ArtDirection.REALISM, ArtDirection.EXPRESSIONISM));

        // Act
        artWorkRequest.setArtDirections(artDirections);

        // Assert
        assertEquals(artDirections, artWorkRequest.getArtDirections());
    }

}