package com.artconnect.backend.model.gallery;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class GalleryCategoryTest {

    @Test
    @DisplayName("Test if enum constants are not null")
    void testEnumConstantsNotNull() {
        assertNotNull(GalleryCategory.PRINT);
        assertNotNull(GalleryCategory.PAINTING);
        assertNotNull(GalleryCategory.DRAWING_ILLUSTRATION);
        assertNotNull(GalleryCategory.PHOTOGRAPHY);
    }

    @Test
    @DisplayName("Test enum constant values")
    void testEnumConstantValues() {
        assertEquals("PRINT", GalleryCategory.PRINT.name());
        assertEquals("PAINTING", GalleryCategory.PAINTING.name());
        assertEquals("DRAWING_ILLUSTRATION", GalleryCategory.DRAWING_ILLUSTRATION.name());
        assertEquals("PHOTOGRAPHY", GalleryCategory.PHOTOGRAPHY.name());
    }

    @Test
    @DisplayName("Test valueOf() method for existing enum constant")
    void testValueOfExistingEnumConstant() {
        GalleryCategory category = GalleryCategory.valueOf("PAINTING");
        assertEquals(GalleryCategory.PAINTING, category);
    }

    @Test
    @DisplayName("Test valueOf() method for non-existing enum constant")
    void testValueOfNonExistingEnumConstant() {
        assertThrows(IllegalArgumentException.class, () -> GalleryCategory.valueOf("UNKNOWN_CATEGORY"));
    }

    @Test
    @DisplayName("Test getting enum constant by ordinal")
    void testGetEnumConstantByOrdinal() {
        GalleryCategory[] categories = GalleryCategory.values();
        assertEquals(GalleryCategory.PRINT, categories[0]);
        assertEquals(GalleryCategory.PAINTING, categories[1]);
        assertEquals(GalleryCategory.DRAWING_ILLUSTRATION, categories[2]);
        assertEquals(GalleryCategory.PHOTOGRAPHY, categories[3]);
    }

    @Test
    @DisplayName("Test toString() method")
    void testToString() {
        assertEquals("PRINT", GalleryCategory.PRINT.toString());
        assertEquals("PAINTING", GalleryCategory.PAINTING.toString());
        assertEquals("DRAWING_ILLUSTRATION", GalleryCategory.DRAWING_ILLUSTRATION.toString());
        assertEquals("PHOTOGRAPHY", GalleryCategory.PHOTOGRAPHY.toString());
    }

    @Test
    @DisplayName("Test equality of enum constants")
    void testEqualityOfEnumConstants() {
        assertNotSame(GalleryCategory.PRINT, GalleryCategory.PAINTING);
        assertNotSame(GalleryCategory.DRAWING_ILLUSTRATION, GalleryCategory.PHOTOGRAPHY);
        assertSame(GalleryCategory.PRINT, GalleryCategory.PRINT);
    }

    @Test
    @DisplayName("Test count of enum constants")
    void testCountOfEnumConstants() {
        assertEquals(4, GalleryCategory.values().length);
    }

}
