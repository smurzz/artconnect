package com.artconnect.backend.model.artwork;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArtDirectionTest {

    @Test
    @DisplayName("Test enum values")
    public void testEnumValues() {
        assertNotNull(ArtDirection.ABSTRACT);
        assertNotNull(ArtDirection.REALISM);
        assertNotNull(ArtDirection.IMPRESSIONISM);
        assertNotNull(ArtDirection.SURREALISM);
        assertNotNull(ArtDirection.EXPRESSIONISM);
        assertNotNull(ArtDirection.MINIMALISM);
        assertNotNull(ArtDirection.CUBISM);
        assertNotNull(ArtDirection.POP_ART);
        assertNotNull(ArtDirection.CONCEPTUAL_ART);
        assertNotNull(ArtDirection.STREET_ART_GRAFFITI);
    }

    @Test
    @DisplayName("Test enum names")
    public void testEnumNames() {
        assertEquals("ABSTRACT", ArtDirection.ABSTRACT.name());
        assertEquals("REALISM", ArtDirection.REALISM.name());
        assertEquals("IMPRESSIONISM", ArtDirection.IMPRESSIONISM.name());
        assertEquals("SURREALISM", ArtDirection.SURREALISM.name());
        assertEquals("EXPRESSIONISM", ArtDirection.EXPRESSIONISM.name());
        assertEquals("MINIMALISM", ArtDirection.MINIMALISM.name());
        assertEquals("CUBISM", ArtDirection.CUBISM.name());
        assertEquals("POP_ART", ArtDirection.POP_ART.name());
        assertEquals("CONCEPTUAL_ART", ArtDirection.CONCEPTUAL_ART.name());
        assertEquals("STREET_ART_GRAFFITI", ArtDirection.STREET_ART_GRAFFITI.name());
    }

    @Test
    @DisplayName("Test enum ordinal values")
    public void testEnumOrdinalValues() {
        assertEquals(0, ArtDirection.ABSTRACT.ordinal());
        assertEquals(1, ArtDirection.REALISM.ordinal());
        assertEquals(2, ArtDirection.IMPRESSIONISM.ordinal());
        assertEquals(3, ArtDirection.SURREALISM.ordinal());
        assertEquals(4, ArtDirection.EXPRESSIONISM.ordinal());
        assertEquals(5, ArtDirection.MINIMALISM.ordinal());
        assertEquals(6, ArtDirection.CUBISM.ordinal());
        assertEquals(7, ArtDirection.POP_ART.ordinal());
        assertEquals(8, ArtDirection.CONCEPTUAL_ART.ordinal());
        assertEquals(9, ArtDirection.STREET_ART_GRAFFITI.ordinal());
    }
}
