package com.artconnect.backend.controller.response;

import com.artconnect.backend.model.Image;
import com.artconnect.backend.model.artwork.ArtDirection;
import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.model.artwork.Comment;
import com.artconnect.backend.model.artwork.Dimension;
import com.artconnect.backend.model.user.Status;
import com.artconnect.backend.model.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

class ArtWorkResponseTest {

    private ArtWork artwork;
    private List<Image> images;
    private boolean isLiked;

    @BeforeEach
    void setUp() {
        artwork = new ArtWork();
        images = Arrays.asList(new Image(), new Image());
        isLiked = true;
    }

    @Test
    void testFromArtWork() {
        artwork.setId("123");
        artwork.setTitle("Artwork Title");
        artwork.setDescription("Artwork Description");
        artwork.setYearOfCreation(2021);
        artwork.setMaterials(Arrays.asList("Material 1", "Material 2"));
        artwork.setTags(Arrays.asList("Tag 1", "Tag 2"));
        artwork.setArtDirections(new HashSet<>(Arrays.asList(
                ArtDirection.ABSTRACT, ArtDirection.REALISM, ArtDirection.IMPRESSIONISM)));
        artwork.setDimension(new Dimension());
        artwork.setPrice(100.0);
        artwork.setLocation("Location");
        artwork.setCreatedAt(new Date());
        List<Comment> comments = Arrays.asList(
                createComment("Comment 1"),
                createComment("Comment 2")
        );
        artwork.setComments(comments);
        artwork.setOwnerId("456");
        artwork.setGalleryId("789");
        artwork.setOwnerName("Owner Name");
        artwork.setGalleryTitle("Gallery Title");

        ArtWorkResponse artworkResponse = ArtWorkResponse.fromArtWork(artwork, images, isLiked);

        Assertions.assertEquals("123", artworkResponse.getId());
        Assertions.assertEquals("Artwork Title", artworkResponse.getTitle());
        Assertions.assertEquals(images, artworkResponse.getImages());
        Assertions.assertEquals("Artwork Description", artworkResponse.getDescription());
        Assertions.assertEquals(2021, artworkResponse.getYearOfCreation());
        Assertions.assertEquals(Arrays.asList("Material 1", "Material 2"), artworkResponse.getMaterials());
        Assertions.assertEquals(Arrays.asList("Tag 1", "Tag 2"), artworkResponse.getTags());
        Assertions.assertEquals(new HashSet<>(Arrays.asList(
                        ArtDirection.ABSTRACT, ArtDirection.REALISM, ArtDirection.IMPRESSIONISM)),
                artworkResponse.getArtDirections());
        Assertions.assertEquals(new Dimension(), artworkResponse.getDimension());
        Assertions.assertEquals(100.0, artworkResponse.getPrice());
        Assertions.assertEquals("Location", artworkResponse.getLocation());
        Assertions.assertEquals(artwork.getCreatedAt(), artworkResponse.getCreatedAt());
        Assertions.assertEquals(comments, artworkResponse.getComments());
        Assertions.assertEquals("456", artworkResponse.getOwnerId());
        Assertions.assertEquals("789", artworkResponse.getGalleryId());
        Assertions.assertEquals("Owner Name", artworkResponse.getOwnerName());
        Assertions.assertEquals("Gallery Title", artworkResponse.getGalleryTitle());
        Assertions.assertTrue(artworkResponse.isLikedByCurrentUser());
    }

    @Test
    void testNoArgsConstructor() {
        ArtWorkResponse artworkResponse = new ArtWorkResponse();
        Assertions.assertNull(artworkResponse.getId());
        Assertions.assertNull(artworkResponse.getTitle());
        Assertions.assertNull(artworkResponse.getImages());
        Assertions.assertNull(artworkResponse.getDescription());
        Assertions.assertNull(artworkResponse.getYearOfCreation());
        Assertions.assertNull(artworkResponse.getMaterials());
        Assertions.assertNull(artworkResponse.getTags());
        Assertions.assertNull(artworkResponse.getArtDirections());
        Assertions.assertNull(artworkResponse.getDimension());
        Assertions.assertNull(artworkResponse.getPrice());
        Assertions.assertNull(artworkResponse.getLocation());
        Assertions.assertNull(artworkResponse.getCreatedAt());
        Assertions.assertNull(artworkResponse.getComments());
        Assertions.assertNull(artworkResponse.getOwnerId());
        Assertions.assertNull(artworkResponse.getGalleryId());
        Assertions.assertNull(artworkResponse.getOwnerName());
        Assertions.assertNull(artworkResponse.getGalleryTitle());
        Assertions.assertNull(artworkResponse.getLikes());
        Assertions.assertFalse(artworkResponse.isLikedByCurrentUser());
    }

    @Test
    void testSetterGetter() {
        ArtWorkResponse artworkResponse = new ArtWorkResponse();

        artworkResponse.setId("123");
        artworkResponse.setTitle("Artwork Title");

        List<Image> images = new ArrayList<>();
        images.add(new Image());
        artworkResponse.setImages(images);

        artworkResponse.setDescription("Artwork Description");
        artworkResponse.setYearOfCreation(2021);

        List<String> materials = new ArrayList<>();
        materials.add("Material 1");
        artworkResponse.setMaterials(materials);

        List<String> tags = new ArrayList<>();
        tags.add("Tag 1");
        artworkResponse.setTags(tags);

        Set<ArtDirection> artDirections = Set.of(ArtDirection.ABSTRACT, ArtDirection.REALISM);
        artworkResponse.setArtDirections(artDirections);

        Dimension dimension = new Dimension();
        dimension.setWidth(100.00);
        dimension.setHeight(200.00);
        artworkResponse.setDimension(dimension);

        artworkResponse.setPrice(100.0);
        artworkResponse.setLocation("Location");
        artworkResponse.setCreatedAt(new Date());

        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment());
        artworkResponse.setComments(comments);

        artworkResponse.setOwnerId("456");
        artworkResponse.setGalleryId("789");
        artworkResponse.setOwnerName("Owner Name");
        artworkResponse.setGalleryTitle("Gallery Title");

        artworkResponse.setLikes(10);
        artworkResponse.setLikedByCurrentUser(true);

        Assertions.assertEquals("123", artworkResponse.getId());
        Assertions.assertEquals("Artwork Title", artworkResponse.getTitle());
        Assertions.assertEquals(images, artworkResponse.getImages());
        Assertions.assertEquals("Artwork Description", artworkResponse.getDescription());
        Assertions.assertEquals(2021, artworkResponse.getYearOfCreation());
        Assertions.assertEquals(materials, artworkResponse.getMaterials());
        Assertions.assertEquals(tags, artworkResponse.getTags());
        Assertions.assertEquals(artDirections, artworkResponse.getArtDirections());
        Assertions.assertEquals(dimension, artworkResponse.getDimension());
        Assertions.assertEquals(100.0, artworkResponse.getPrice());
        Assertions.assertEquals("Location", artworkResponse.getLocation());
        Assertions.assertEquals(comments, artworkResponse.getComments());
        Assertions.assertEquals("456", artworkResponse.getOwnerId());
        Assertions.assertEquals("789", artworkResponse.getGalleryId());
        Assertions.assertEquals("Owner Name", artworkResponse.getOwnerName());
        Assertions.assertEquals("Gallery Title", artworkResponse.getGalleryTitle());
        Assertions.assertEquals(10, artworkResponse.getLikes());
        Assertions.assertTrue(artworkResponse.isLikedByCurrentUser());
    }

    private Comment createComment(String text) {
        Comment comment = new Comment();
        comment.setText(text);
        return comment;
    }
}
