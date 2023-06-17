package com.artconnect.backend.model.artwork;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class DimensionTest {
    private Dimension dimension;

    @BeforeEach
    public void setup() {
        dimension = new Dimension();
    }

    @Test
    public void testSetAndGetHeight() {
        double expectedHeight = 10.0;
        dimension.setHeight(expectedHeight);

        double actualHeight = dimension.getHeight();

        assertEquals(expectedHeight, actualHeight);
    }

    @Test
    public void testSetAndGetWidth() {
        double expectedWidth = 15.0;
        dimension.setWidth(expectedWidth);

        double actualWidth = dimension.getWidth();

        assertEquals(expectedWidth, actualWidth);
    }

    @Test
    public void testSetAndGetDepth() {
        double expectedDepth = 5.0;
        dimension.setDepth(expectedDepth);

        double actualDepth = dimension.getDepth();

        assertEquals(expectedDepth, actualDepth);
    }

    @Test
    public void testBuilderConstructor() {
        double expectedHeight = 10.0;
        double expectedWidth = 15.0;
        double expectedDepth = 5.0;

        Dimension dimension = Dimension.builder()
                .height(expectedHeight)
                .width(expectedWidth)
                .depth(expectedDepth)
                .build();

        assertEquals(expectedHeight, dimension.getHeight());
        assertEquals(expectedWidth, dimension.getWidth());
        assertEquals(expectedDepth, dimension.getDepth());
    }

    @Test
    public void testDefaultConstructor() {
        assertEquals(0.0, dimension.getHeight());
        assertEquals(0.0, dimension.getWidth());
        assertEquals(0.0, dimension.getDepth());
    }

    @Test
    public void testAllArgsConstructor() {
        double expectedHeight = 10.0;
        double expectedWidth = 15.0;
        double expectedDepth = 5.0;

        Dimension dimension = new Dimension(expectedHeight, expectedWidth, expectedDepth);

        assertEquals(expectedHeight, dimension.getHeight());
        assertEquals(expectedWidth, dimension.getWidth());
        assertEquals(expectedDepth, dimension.getDepth());
    }


    @Test
    public void testEqualsAndHashCode() {
        Dimension dimension1 = new Dimension(10.0, 15.0, 5.0);
        Dimension dimension2 = new Dimension(10.0, 15.0, 5.0);
        Dimension dimension3 = new Dimension(5.0, 10.0, 15.0);

        assertEquals(dimension1, dimension2);
        assertNotEquals(dimension1, dimension3);

        assertEquals(dimension1.hashCode(), dimension2.hashCode());
        assertNotEquals(dimension1.hashCode(), dimension3.hashCode());
    }

    @Test
    public void testToString() {
        Dimension dimension = new Dimension(10.0, 15.0, 5.0);

        String expectedToString = "Dimension(height=10.0, width=15.0, depth=5.0)";
        assertEquals(expectedToString, dimension.toString());
    }



    @Test
    public void testSetterAndGettersWithNonNullValues() {
        double expectedHeight = 10.0;
        double expectedWidth = 15.0;
        double expectedDepth = 5.0;

        dimension = new Dimension();

        dimension.setHeight(expectedHeight);
        dimension.setWidth(expectedWidth);
        dimension.setDepth(expectedDepth);

        assertEquals(expectedHeight, dimension.getHeight());
        assertEquals(expectedWidth, dimension.getWidth());
        assertEquals(expectedDepth, dimension.getDepth());
    }

    @Test
    public void testSetterAndGettersWithDefaultValues() {
        dimension = new Dimension();

        dimension.setHeight(0.0);
        dimension.setWidth(0.0);
        dimension.setDepth(0.0);

        assertEquals(0.0, dimension.getHeight());
        assertEquals(0.0, dimension.getWidth());
        assertEquals(0.0, dimension.getDepth());
    }

    @Test
    public void testEqualsAndHashCodeWithNull() {
        Dimension dimension1 = new Dimension();
        Dimension dimension2 = new Dimension();

        assertEquals(dimension1, dimension2);
        assertEquals(dimension1.hashCode(), dimension2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentObjects() {
        Dimension dimension = new Dimension();

        assertNotEquals(dimension, new Object());
    }

    @Test
    public void testEqualsAndHashCodeWithSameObject() {
        assertEquals(dimension, dimension);
        assertEquals(dimension.hashCode(), dimension.hashCode());
    }

    @Test
    public void testToStringWithDefaultValues() {
        String expectedToString = "Dimension(height=0.0, width=0.0, depth=0.0)";
        assertEquals(expectedToString, dimension.toString());
    }

    @Test
    public void testToStringWithCustomValues() {
        dimension.setHeight(10.0);
        dimension.setWidth(15.0);
        dimension.setDepth(5.0);

        String expectedToString = "Dimension(height=10.0, width=15.0, depth=5.0)";
        assertEquals(expectedToString, dimension.toString());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentValues() {
        Dimension dimension1 = new Dimension(10.0, 15.0, 5.0);
        Dimension dimension2 = new Dimension(10.0, 15.0, 5.0);
        Dimension dimension3 = new Dimension(5.0, 10.0, 15.0);

        assertNotEquals(dimension1, dimension3);
        assertNotEquals(dimension1.hashCode(), dimension3.hashCode());

        assertEquals(dimension1, dimension2);
        assertEquals(dimension1.hashCode(), dimension2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithUpdatedValues() {
        Dimension dimension1 = new Dimension(10.0, 15.0, 5.0);
        Dimension dimension2 = new Dimension(10.0, 15.0, 5.0);

        dimension1.setHeight(20.0);
        dimension2.setWidth(25.0);

        assertNotEquals(dimension1, dimension2);
        assertNotEquals(dimension1.hashCode(), dimension2.hashCode());
    }

    @Test
    public void testSetterAndGettersWithNegativeValues() {
        double expectedHeight = -10.0;
        double expectedWidth = -15.0;
        double expectedDepth = -5.0;

        dimension.setHeight(expectedHeight);
        dimension.setWidth(expectedWidth);
        dimension.setDepth(expectedDepth);

        assertEquals(expectedHeight, dimension.getHeight());
        assertEquals(expectedWidth, dimension.getWidth());
        assertEquals(expectedDepth, dimension.getDepth());
    }

    @Test
    public void testEqualsAndHashCodeWithSameInstance() {
        Dimension dimension1 = dimension;
        Dimension dimension2 = dimension;

        assertEquals(dimension1, dimension2);
        assertEquals(dimension1.hashCode(), dimension2.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentClass() {
        Dimension dimension1 = new Dimension();
        String dimension2 = "dimension";

        assertNotEquals(dimension1, dimension2);
    }

    @Test
    public void testSetterAndGettersWithNullValues() {
        dimension = new Dimension();

        dimension.setHeight(0.0);
        dimension.setWidth(0.0);
        dimension.setDepth(0.0);

        assertEquals(0.0, dimension.getHeight());
        assertEquals(0.0, dimension.getWidth());
        assertEquals(0.0, dimension.getDepth());
    }

}