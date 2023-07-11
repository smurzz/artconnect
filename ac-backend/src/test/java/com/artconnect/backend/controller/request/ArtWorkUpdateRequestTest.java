package com.artconnect.backend.controller.request;

import static com.mongodb.assertions.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.artconnect.backend.model.artwork.ArtDirection;
import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.model.artwork.Comment;
import com.artconnect.backend.model.artwork.Dimension;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ArtWorkUpdateRequestTest {



    @Test
    public void testId() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        request.setId("testId");
        assertEquals("testId", request.getId());

    }



    @Test
    public void testNoArgsConstructor() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        assertNull(request.getId());


    }



    @Test
    public void testOwnerId() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        request.setOwnerId("testOwnerId");
        assertEquals("testOwnerId", request.getOwnerId());


    }



    @Test
    public void testNoArgsConstructor2() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        assertNull(request.getOwnerId());
    }




    @Test
    public void testGalleryId() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        request.setGalleryId("testGalleryId");
        assertEquals("testGalleryId", request.getGalleryId());
    }



    @Test
    public void testNoArgsConstructor3() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        assertNull(request.getGalleryId());


    }




    @Test
    public void testOwnerName() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        request.setOwnerName("testOwnerName");
        assertEquals("testOwnerName", request.getOwnerName());
    }



    @Test
    public void testNoArgsConstructor4() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        assertNull(request.getOwnerName());


    }


    @Test
    public void testGalleryTitle() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        request.setGalleryTitle("testGalleryTitle");
        assertEquals("testGalleryTitle", request.getGalleryTitle());


    }



    @Test
    public void testNoArgsConstructor5() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        assertNull(request.getGalleryTitle());

    }



    @Test
    public void testTitle() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        request.setTitle("testTitle");
        assertEquals("testTitle", request.getTitle());

    }




    @Test
    public void testNoArgsConstructor6() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        assertNull(request.getTitle());


    }





    @Test
    public void testDescription() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        request.setDescription("testDescription");
        assertEquals("testDescription", request.getDescription());

    }




    @Test
    public void testNoArgsConstructor7() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        assertNull(request.getDescription());

    }




    @Test
    public void testYearOfCreation() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        request.setYearOfCreation(2022);
        assertEquals(2022, request.getYearOfCreation());


    }




    @Test
    public void testNoArgsConstructor8() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        assertNull(request.getYearOfCreation());

    }



    @Test
    public void testMaterials() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        List<String> materials = Arrays.asList("Oil", "Canvas");
        request.setMaterials(materials);
        assertEquals(materials, request.getMaterials());

    }

    @Test
    public void testNoArgsConstructor9() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        assertNull(request.getMaterials());

    }




    @Test
    public void testDimension() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        Dimension dimension = new Dimension(10.0, 20.0, 30.0);
        request.setDimension(dimension);
        assertEquals(dimension, request.getDimension());


    }




    @Test
    public void testNoArgsConstructor10() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        assertNull(request.getDimension());
    }




    @Test
    public void testPrice() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        request.setPrice(100.0);
        assertEquals(100.0, request.getPrice());
    }




    @Test
    public void testNoArgsConstructor11() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        assertNull(request.getPrice());


    }



    @Test
    public void testTags() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        List<String> tags = Arrays.asList("Oil", "Canvas");
        request.setTags(tags);
        assertEquals(tags, request.getTags());


    }



    @Test
    public void testNoArgsConstructor12() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        assertNull(request.getTags());


    }



    @Test
    public void testArtDirections() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        Set<ArtDirection> artDirections = new HashSet<>();
        artDirections.add(ArtDirection.ABSTRACT);
        artDirections.add(ArtDirection.REALISM);
        request.setArtDirections(artDirections);
        assertEquals(artDirections, request.getArtDirections());
    }




    @Test
    public void testNoArgsConstructor13() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        assertNull(request.getArtDirections());
    }



    @Test
    public void testLocation() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        request.setLocation("testLocation");
        assertEquals("testLocation", request.getLocation());
    }



    @Test
    public void testImagesIds() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        List<String> imagesIds = Arrays.asList("image1", "image2");
        request.setImagesIds(imagesIds);
        assertEquals(imagesIds, request.getImagesIds());
    }



    @Test
    public void testLikedByUsers() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        Set<String> likedByUsers = new HashSet<>();
        likedByUsers.add("user1");
        likedByUsers.add("user2");
        request.setLikedByUsers(likedByUsers);
        assertEquals(likedByUsers, request.getLikedByUsers());
    }




    @Test
    public void testComments() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        Date date = new Date();
        List<Comment> comments = Arrays.asList(
                Comment.builder()
                        .id("comment1")
                        .authorId("user1")
                        .authorName("User 1")
                        .createdAt(date)
                        .isUpdated(false)
                        .text("Comment 1")
                        .build(),
                Comment.builder()
                        .id("comment2")
                        .authorId("user2")
                        .authorName("User 2")
                        .createdAt(date)
                        .isUpdated(false)
                        .text("Comment 2")
                        .build()
        );
        request.setComments(comments);
        assertEquals(comments, request.getComments());
    }



    @Test
    public void testNoArgsConstructor14() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        assertNull(request.getLocation());
        assertNull(request.getImagesIds());
        assertNull(request.getLikedByUsers());
        assertNull(request.getComments());


    }



    @Test
    public void testToArtWork() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        request.setId("testId");
        request.setTitle("testTitle");
        List<String> imagesIds = Arrays.asList("image1", "image2");
        request.setImagesIds(imagesIds);
        request.setDescription("testDescription");
        request.setYearOfCreation(2022);
        List<String> materials = Arrays.asList("Oil", "Canvas");
        request.setMaterials(materials);
        List<String> tags = Arrays.asList("tag1", "tag2");
        request.setTags(tags);
        Set<ArtDirection> artDirections = new HashSet<>();
        artDirections.add(ArtDirection.ABSTRACT);
        artDirections.add(ArtDirection.REALISM);
        request.setArtDirections(artDirections);
        Dimension dimension = new Dimension(10.0, 20.0, 30.0);
        request.setDimension(dimension);
        request.setPrice(100.0);
        request.setLocation("testLocation");
        List<Comment> comments = Arrays.asList(new Comment("comment1", "user1", "User 1", null, false, null, "Comment 1"));
        request.setComments(comments);
        request.setOwnerId("testOwnerId");
        request.setGalleryId("testGalleryId");
        request.setOwnerName("testOwnerName");
        request.setGalleryTitle("testGalleryTitle");
        Set<String> likedByUsers = new HashSet<>();
        likedByUsers.add("user1");
        likedByUsers.add("user2");
        request.setLikedByUsers(likedByUsers);

        ArtWork artwork = ArtWorkUpdateRequest.toArtWork(request);

        assertEquals(request.getId(), artwork.getId());
        assertEquals(request.getTitle(), artwork.getTitle());
        assertEquals(request.getImagesIds(), artwork.getImagesIds());
        assertEquals(request.getDescription(), artwork.getDescription());
        assertEquals(request.getYearOfCreation(), artwork.getYearOfCreation());
        assertEquals(request.getMaterials(), artwork.getMaterials());
        assertEquals(request.getTags(), artwork.getTags());
        assertEquals(request.getArtDirections(), artwork.getArtDirections());
        assertEquals(request.getDimension(), artwork.getDimension());
        assertEquals(request.getPrice(), artwork.getPrice());
        assertEquals(request.getLocation(), artwork.getLocation());
        assertEquals(request.getComments(), artwork.getComments());
        assertEquals(request.getOwnerId(), artwork.getOwnerId());
        assertEquals(request.getGalleryId(), artwork.getGalleryId());
        assertEquals(request.getOwnerName(), artwork.getOwnerName());
        assertEquals(request.getGalleryTitle(), artwork.getGalleryTitle());
    }




}
