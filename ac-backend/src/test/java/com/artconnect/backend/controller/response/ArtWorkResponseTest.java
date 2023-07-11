package com.artconnect.backend.controller.response;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

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
}

