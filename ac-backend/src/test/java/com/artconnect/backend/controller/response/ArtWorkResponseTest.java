package com.artconnect.backend.controller.response;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.artconnect.backend.model.artwork.ArtDirection;
import org.junit.jupiter.api.Test;

import com.artconnect.backend.controller.response.ArtWorkResponse;
import com.artconnect.backend.model.Image;

public class ArtWorkResponseTest {

    @Test
    public void testArtWorkResponse() {
        ArtWorkResponse response = new ArtWorkResponse();
        response.setId("1");
        response.setTitle("Title");
        response.setImages(Arrays.asList(new Image()));
        response.setDescription("Description");
        response.setYearOfCreation(2022);
        response.setMaterials(Arrays.asList("Material1", "Material2"));
        response.setTags(Arrays.asList("Tag1", "Tag2"));

        assertEquals("1", response.getId());
        assertEquals("Title", response.getTitle());
        assertEquals(1, response.getImages().size());
        assertEquals("Description", response.getDescription());
        assertEquals(2022, response.getYearOfCreation());
        assertEquals(2, response.getMaterials().size());
        assertEquals(2, response.getTags().size());
    }

    @Test
    public void testArtWorkResponse2() {
        ArtWorkResponse response = new ArtWorkResponse();
        Set<ArtDirection> artDirections = new HashSet<>();
        artDirections.add(ArtDirection.ABSTRACT);
        artDirections.add(ArtDirection.REALISM);
        response.setArtDirections(artDirections);

        assertEquals(2, response.getArtDirections().size());
        assertTrue(response.getArtDirections().contains(ArtDirection.ABSTRACT));
        assertTrue(response.getArtDirections().contains(ArtDirection.REALISM));
    }


}

