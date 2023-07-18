package com.artconnect.backend.controller.request;

import static org.junit.jupiter.api.Assertions.*;

import com.artconnect.backend.model.artwork.ArtDirection;
import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.model.artwork.Comment;
import com.artconnect.backend.model.artwork.Dimension;
import org.junit.jupiter.api.Test;

import java.util.*;

class ArtWorkUpdateRequestTest {

    @Test
    void toArtWork_ConvertsRequestToArtWork() {
        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest();
        request.setId("123");
        request.setTitle("Artwork Title");
        request.setImagesIds(Arrays.asList("image1", "image2"));
        request.setDescription("Artwork description");
        request.setYearOfCreation(2021);
        request.setMaterials(Arrays.asList("material1", "material2"));
        request.setTags(Arrays.asList("tag1", "tag2"));
        request.setArtDirections(new HashSet<>(Arrays.asList(ArtDirection.ABSTRACT, ArtDirection.REALISM)));
        request.setDimension(new Dimension(10.0, 20.0, 5.0));
        request.setPrice(100.0);
        request.setLocation("Artwork location");

        List<Comment> comments = new ArrayList<>();
        Comment comment1 = new Comment();
        comment1.setId("1");
        comment1.setAuthorId("author1");
        comment1.setAuthorName("Author 1");
        comment1.setCreatedAt(new Date());
        comment1.setUpdated(false);
        comment1.setText("Comment 1");
        comments.add(comment1);

        Comment comment2 = new Comment();
        comment2.setId("2");
        comment2.setAuthorId("author2");
        comment2.setAuthorName("Author 2");
        comment2.setCreatedAt(new Date());
        comment2.setUpdated(false);
        comment2.setText("Comment 2");
        comments.add(comment2);

        request.setComments(comments);
        request.setOwnerId("ownerId");
        request.setGalleryId("galleryId");
        request.setOwnerName("Owner Name");
        request.setGalleryTitle("Gallery Title");
        request.setLikedByUsers(Collections.singleton("user1"));

        ArtWork artwork = ArtWorkUpdateRequest.toArtWork(request);

        assertEquals("123", artwork.getId());
        assertEquals("Artwork Title", artwork.getTitle());
        assertEquals(Arrays.asList("image1", "image2"), artwork.getImagesIds());
        assertEquals("Artwork description", artwork.getDescription());
        assertEquals(2021, artwork.getYearOfCreation());
        assertEquals(Arrays.asList("material1", "material2"), artwork.getMaterials());
        assertEquals(Arrays.asList("tag1", "tag2"), artwork.getTags());
        assertEquals(new HashSet<>(Arrays.asList(ArtDirection.ABSTRACT, ArtDirection.REALISM)), artwork.getArtDirections());
        assertEquals(new Dimension(10.0, 20.0, 5.0), artwork.getDimension());
        assertEquals(100.0, artwork.getPrice());
        assertEquals("Artwork location", artwork.getLocation());
        assertEquals(comments, artwork.getComments());
        assertEquals("ownerId", artwork.getOwnerId());
        assertEquals("galleryId", artwork.getGalleryId());
        assertEquals("Owner Name", artwork.getOwnerName());
        assertEquals("Gallery Title", artwork.getGalleryTitle());
        assertEquals(Collections.singleton("user1"), artwork.getLikedByUsers());
    }


    @Test
    void allArgsConstructor_CreatesObjectWithAllFields() {
        String id = "123";
        String ownerId = "ownerId";
        String galleryId = "galleryId";
        String ownerName = "Owner Name";
        String galleryTitle = "Gallery Title";
        String title = "Artwork Title";
        String description = "Artwork description";
        Integer yearOfCreation = 2021;
        List<String> materials = Arrays.asList("material1", "material2");
        Dimension dimension = new Dimension(10.0, 20.0, 5.0);
        Double price = 100.0;
        List<String> tags = Arrays.asList("tag1", "tag2");
        Set<ArtDirection> artDirections = new HashSet<>(Arrays.asList(ArtDirection.ABSTRACT, ArtDirection.REALISM));
        String location = "Artwork location";
        List<String> imagesIds = Arrays.asList("image1", "image2");
        Set<String> likedByUsers = Collections.singleton("user1");
        List<Comment> comments = Arrays.asList(new Comment(), new Comment());

        ArtWorkUpdateRequest request = new ArtWorkUpdateRequest(
                id, ownerId, galleryId, ownerName, galleryTitle, title, description, yearOfCreation, materials,
                dimension, price, tags, artDirections, location, imagesIds, likedByUsers, comments
        );

        assertEquals(id, request.getId());
        assertEquals(ownerId, request.getOwnerId());
        assertEquals(galleryId, request.getGalleryId());
        assertEquals(ownerName, request.getOwnerName());
        assertEquals(galleryTitle, request.getGalleryTitle());
        assertEquals(title, request.getTitle());
        assertEquals(description, request.getDescription());
        assertEquals(yearOfCreation, request.getYearOfCreation());
        assertEquals(materials, request.getMaterials());
        assertEquals(dimension, request.getDimension());
        assertEquals(price, request.getPrice());
        assertEquals(tags, request.getTags());
        assertEquals(artDirections, request.getArtDirections());
        assertEquals(location, request.getLocation());
        assertEquals(imagesIds, request.getImagesIds());
        assertEquals(likedByUsers, request.getLikedByUsers());
        assertEquals(comments, request.getComments());
    }

}
