package com.artconnect.backend.controller.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.artconnect.backend.model.gallery.GalleryCategory;
import org.junit.jupiter.api.Test;

import java.util.*;

public class GalleryRequestTest {



    @Test
    public void testId() {
        GalleryRequest request = new GalleryRequest();
        request.setId("testId");
        assertEquals("testId", request.getId());
    }



    @Test
    public void testOwnerId() {
        GalleryRequest request = new GalleryRequest();
        request.setOwnerId("testOwnerId");
        assertEquals("testOwnerId", request.getOwnerId());
    }



    @Test
    public void testOwnerName() {
        GalleryRequest request = new GalleryRequest();
        request.setOwnerName("testOwnerName");
        assertEquals("testOwnerName", request.getOwnerName());
    }




    @Test
    public void testTitle() {
        GalleryRequest request = new GalleryRequest();
        request.setTitle("testTitle");
        assertEquals("testTitle", request.getTitle());
    }



    @Test
    public void testDescription() {
        GalleryRequest request = new GalleryRequest();
        request.setDescription("testDescription");
        assertEquals("testDescription", request.getDescription());
    }





    @Test
    public void testNoArgsConstructor2() {
        GalleryRequest request = new GalleryRequest();
        assertNull(request.getId());
        assertNull(request.getOwnerId());
        assertNull(request.getOwnerName());
        assertNull(request.getTitle());
        assertNull(request.getDescription());
    }


    @Test
    public void testCategories() {
        GalleryRequest request = new GalleryRequest();
        Set<GalleryCategory> categories = new HashSet<>();
        categories.add(GalleryCategory.PAINTING);
        categories.add(GalleryCategory.PHOTOGRAPHY);
        request.setCategories(categories);
        assertEquals(categories, request.getCategories());
    }

    @Test
    public void testArtworkIds() {
        GalleryRequest request = new GalleryRequest();
        List<String> artworkIds = Arrays.asList("artwork1", "artwork2");
        request.setArtworkIds(artworkIds);
        assertEquals(artworkIds, request.getArtworkIds());
    }

    @Test
    public void testEvaluations() {
        GalleryRequest request = new GalleryRequest();
        Map<String, Integer> evaluations = new HashMap<>();
        evaluations.put("user1", 5);
        evaluations.put("user2", 4);
        request.setEvaluations(evaluations);
        assertEquals(evaluations, request.getEvaluations());
    }

    @Test
    public void testNoArgsConstructor() {
        GalleryRequest request = new GalleryRequest();
        assertNull(request.getCategories());
        assertNull(request.getArtworkIds());
        assertNull(request.getEvaluations());
    }





}
