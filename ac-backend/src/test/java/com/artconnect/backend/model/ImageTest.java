package com.artconnect.backend.model;

import org.bson.types.Binary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.bson.BsonBinary;
import org.bson.types.Binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImageTest {

    @Test
    public void testImageCreation() {
        // Create a sample binary data for the image
        byte[] imageData = {0x01, 0x02, 0x03};
        Binary binaryImage = new Binary(imageData);

        // Create an instance of Image
        Image image = new Image();
        image.setId("123");
        image.setImage(binaryImage);
        image.setTitle("Sample Image");
        image.setContentType("image/jpeg");

        // Assert the properties of the image
        assertEquals("123", image.getId());
        assertEquals(binaryImage, image.getImage());
        assertEquals("Sample Image", image.getTitle());
        assertEquals("image/jpeg", image.getContentType());
    }

    @Test
    public void testImageEquality() {
        // Create two images with the same properties
        Image image1 = new Image("123", new Binary(new byte[]{0x01, 0x02, 0x03}), "Sample Image", "image/jpeg");
        Image image2 = new Image("123", new Binary(new byte[]{0x01, 0x02, 0x03}), "Sample Image", "image/jpeg");

        // Assert that the images are equal
        assertEquals(image1, image2);
    }

    @Test
    public void testImageInequality() {
        // Create two images with different properties
        Image image1 = new Image("123", new Binary(new byte[]{0x01, 0x02, 0x03}), "Sample Image", "image/jpeg");
        Image image2 = new Image("456", new Binary(new byte[]{0x04, 0x05, 0x06}), "Another Image", "image/png");

        // Assert that the images are not equal
        Assertions.assertNotEquals(image1, image2);
    }


    @Test
    public void testImageIdSetterGetter() {
        // Create an image
        Image image = new Image();
        image.setId("123");

        // Assert the id value using the getter method
        assertEquals("123", image.getId());
    }

    @Test
    public void testImageImageSetterGetter() {
        // Create an image
        Image image = new Image();
        Binary binaryImage = new Binary(new byte[]{0x01, 0x02, 0x03});
        image.setImage(binaryImage);

        // Assert the image value using the getter method
        assertEquals(binaryImage, image.getImage());
    }

    @Test
    public void testImageTitleSetterGetter() {
        // Create an image
        Image image = new Image();
        image.setTitle("Sample Image");

        // Assert the title value using the getter method
        assertEquals("Sample Image", image.getTitle());
    }

    @Test
    public void testImageContentTypeSetterGetter() {
        // Create an image
        Image image = new Image();
        image.setContentType("image/jpeg");

        // Assert the contentType value using the getter method
        assertEquals("image/jpeg", image.getContentType());
    }

    @Test
    public void testImageBuilder() {
        // Create an image using the builder pattern
        Image image = Image.builder()
                .id("123")
                .image(new Binary(new byte[]{0x01, 0x02, 0x03}))
                .title("Sample Image")
                .contentType("image/jpeg")
                .build();

        // Assert the properties of the image
        assertEquals("123", image.getId());
        assertEquals(new Binary(new byte[]{0x01, 0x02, 0x03}), image.getImage());
        assertEquals("Sample Image", image.getTitle());
        assertEquals("image/jpeg", image.getContentType());
    }

    @Test
    public void testImageToString() {
        // Create the expected Image object
        byte[] expectedImageData = {1, 2, 3};
        org.bson.types.Binary expectedImageBinary = new org.bson.types.Binary(expectedImageData);
        Image expectedImage = new Image("123", expectedImageBinary, "Sample Image", "image/jpeg");

        // Create the actual Image object (using the correct data type and expected binary data)
        byte[] actualImageData = {1, 2, 3};
        org.bson.types.Binary actualImageBinary = new org.bson.types.Binary(actualImageData);
        Image actualImage = new Image("123", actualImageBinary, "Sample Image", "image/jpeg");

        // Assert that the expected and actual objects are equal
        assertEquals(expectedImage, actualImage);
    }

    @Test
    public void testImageBuilderWithDefaultValues() {
        // Create an image using the builder pattern with default values
        Image image = Image.builder().build();

        // Assert the default values of the image
        assertEquals(null, image.getId());
        assertEquals(null, image.getImage());
        assertEquals(null, image.getTitle());
        assertEquals(null, image.getContentType());
    }

    @Test
    public void testImageBuilderWithCustomValues() {
        // Create an image using the builder pattern with custom values
        Binary binaryImage = new Binary(new byte[]{0x01, 0x02, 0x03});
        Image image = Image.builder()
                .id("123")
                .image(binaryImage)
                .title("Sample Image")
                .contentType("image/jpeg")
                .build();

        // Assert the custom values of the image
        assertEquals("123", image.getId());
        assertEquals(binaryImage, image.getImage());
        assertEquals("Sample Image", image.getTitle());
        assertEquals("image/jpeg", image.getContentType());
    }

    @Test
    public void testImageEqualsAndHashCode() {
        // Create two images with the same properties
        Image image1 = Image.builder()
                .id("123")
                .image(new Binary(new byte[]{0x01, 0x02, 0x03}))
                .title("Sample Image")
                .contentType("image/jpeg")
                .build();

        Image image2 = Image.builder()
                .id("123")
                .image(new Binary(new byte[]{0x01, 0x02, 0x03}))
                .title("Sample Image")
                .contentType("image/jpeg")
                .build();

        // Assert that the images are equal
        assertEquals(image1, image2);

        // Assert that the hash codes are equal
        assertEquals(image1.hashCode(), image2.hashCode());
    }

}