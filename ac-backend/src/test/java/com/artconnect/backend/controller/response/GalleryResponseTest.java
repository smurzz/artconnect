package com.artconnect.backend.controller.response;

import com.artconnect.backend.model.gallery.Gallery;
import com.artconnect.backend.model.gallery.GalleryCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GalleryResponseTest {

    private Gallery gallery;
    private List<ArtWorkResponse> artworks;

    @BeforeEach
    void setUp() {
        gallery = new Gallery();
        gallery.setId("123");
        gallery.setOwnerId("456");
        gallery.setOwnerName("John");
        gallery.setTitle("Art Gallery");
        gallery.setDescription("A beautiful art gallery");
        gallery.setCategories(Collections.singleton(GalleryCategory.PAINTING));
        gallery.setEvaluation("user1", 4);
        gallery.setEvaluation("user2", 5);

        artworks = Collections.singletonList(new ArtWorkResponse());
    }

    @Test
    void testFromGallery() {
        GalleryResponse galleryResponse = GalleryResponse.fromGallery(gallery, artworks);

        assertNotNull(galleryResponse);
        assertEquals("123", galleryResponse.getId());
        assertEquals("456", galleryResponse.getOwnerId());
        assertEquals("John", galleryResponse.getOwnerName());
        assertEquals("Art Gallery", galleryResponse.getTitle());
        assertEquals("A beautiful art gallery", galleryResponse.getDescription());
        assertEquals(artworks, galleryResponse.getArtworks());
        assertEquals(Collections.singleton(GalleryCategory.PAINTING), galleryResponse.getCategories());
        assertEquals(4.5, galleryResponse.getRanking());
    }

    @Test
    void testNoArgsConstructor() {
        GalleryResponse galleryResponse = new GalleryResponse();

        assertNotNull(galleryResponse);
        assertNull(galleryResponse.getId());
        assertNull(galleryResponse.getOwnerId());
        assertNull(galleryResponse.getOwnerName());
        assertNull(galleryResponse.getTitle());
        assertNull(galleryResponse.getDescription());
        assertNull(galleryResponse.getArtworks());
        assertNull(galleryResponse.getCategories());
        assertNull(galleryResponse.getRanking());
    }

    @Test
    void testSetterAndGetters() {
        GalleryResponse galleryResponse = new GalleryResponse();

        galleryResponse.setId("123");
        galleryResponse.setOwnerId("456");
        galleryResponse.setOwnerName("John");
        galleryResponse.setTitle("Art Gallery");
        galleryResponse.setDescription("A beautiful art gallery");
        galleryResponse.setArtworks(null);
        galleryResponse.setCategories(null);
        galleryResponse.setRanking(4.5);

        assertEquals("123", galleryResponse.getId());
        assertEquals("456", galleryResponse.getOwnerId());
        assertEquals("John", galleryResponse.getOwnerName());
        assertEquals("Art Gallery", galleryResponse.getTitle());
        assertEquals("A beautiful art gallery", galleryResponse.getDescription());
        assertNull(galleryResponse.getArtworks());
        assertNull(galleryResponse.getCategories());
        assertEquals(4.5, galleryResponse.getRanking());
    }
}