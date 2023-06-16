package com.artconnect.backend.model;

import org.bson.types.Binary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        Assertions.assertEquals("123", image.getId());
        Assertions.assertEquals(binaryImage, image.getImage());
        Assertions.assertEquals("Sample Image", image.getTitle());
        Assertions.assertEquals("image/jpeg", image.getContentType());
    }

    @Test
    public void testImageEquality() {
        // Create two images with the same properties
        Image image1 = new Image("123", new Binary(new byte[]{0x01, 0x02, 0x03}), "Sample Image", "image/jpeg");
        Image image2 = new Image("123", new Binary(new byte[]{0x01, 0x02, 0x03}), "Sample Image", "image/jpeg");

        // Assert that the images are equal
        Assertions.assertEquals(image1, image2);
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
        Assertions.assertEquals("123", image.getId());
    }

    @Test
    public void testImageImageSetterGetter() {
        // Create an image
        Image image = new Image();
        Binary binaryImage = new Binary(new byte[]{0x01, 0x02, 0x03});
        image.setImage(binaryImage);

        // Assert the image value using the getter method
        Assertions.assertEquals(binaryImage, image.getImage());
    }

    @Test
    public void testImageTitleSetterGetter() {
        // Create an image
        Image image = new Image();
        image.setTitle("Sample Image");

        // Assert the title value using the getter method
        Assertions.assertEquals("Sample Image", image.getTitle());
    }

    @Test
    public void testImageContentTypeSetterGetter() {
        // Create an image
        Image image = new Image();
        image.setContentType("image/jpeg");

        // Assert the contentType value using the getter method
        Assertions.assertEquals("image/jpeg", image.getContentType());
    }
}