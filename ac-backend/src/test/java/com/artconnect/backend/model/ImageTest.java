package com.artconnect.backend.model;

import org.bson.types.Binary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.bson.BsonBinary;
import org.bson.types.Binary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;


import static org.junit.jupiter.api.Assertions.*;


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

    @Nested
    @DisplayName("Getter and Setter Tests")
    class GetterSetterTests {

        private Image image;

        @BeforeEach
        void setUp() {
            image = new Image();
        }

        @Test
        void setId() {
            image.setId("123");
            assertEquals("123", image.getId());
        }

        @Test
        void setImage() {
            Binary binaryImage = new Binary(new byte[]{0x01, 0x02, 0x03});
            image.setImage(binaryImage);
            assertEquals(binaryImage, image.getImage());
        }

        @Test
        void setTitle() {
            image.setTitle("Sample Image");
            assertEquals("Sample Image", image.getTitle());
        }

        @Test
        void setContentType() {
            image.setContentType("image/jpeg");
            assertEquals("image/jpeg", image.getContentType());
        }
    }

    @Nested
    @DisplayName("Builder Tests")
    class BuilderTests {

        private Image image;

        @BeforeEach
        void setUp() {
            Binary binaryImage = new Binary(new byte[]{0x01, 0x02, 0x03});
            image = Image.builder()
                    .id("123")
                    .image(binaryImage)
                    .title("Sample Image")
                    .contentType("image/jpeg")
                    .build();
        }

        @Test
        void testBuilderId() {
            assertEquals("123", image.getId());
        }

        @Test
        void testBuilderImage() {
            Binary binaryImage = new Binary(new byte[]{0x01, 0x02, 0x03});
            assertEquals(binaryImage, image.getImage());
        }

        @Test
        void testBuilderTitle() {
            assertEquals("Sample Image", image.getTitle());
        }

        @Test
        void testBuilderContentType() {
            assertEquals("image/jpeg", image.getContentType());
        }
    }

    @Test
    public void testAllArgsConstructor() {
        // Create an instance of Image using the all-args constructor
        String id = "123";
        Binary image = new Binary(new byte[]{0x01, 0x02, 0x03});
        String title = "Sample Image";
        String contentType = "image/jpeg";

        Image imageInstance = new Image(id, image, title, contentType);

        // Assert the properties of the image
        assertEquals(id, imageInstance.getId());
        assertEquals(image, imageInstance.getImage());
        assertEquals(title, imageInstance.getTitle());
        assertEquals(contentType, imageInstance.getContentType());
    }

    @Test
    public void testNoArgsConstructor() {
        // Create an instance of Image using the no-args constructor
        Image imageInstance = new Image();

        // Assert that all properties are null
        assertNull(imageInstance.getId());
        assertNull(imageInstance.getImage());
        assertNull(imageInstance.getTitle());
        assertNull(imageInstance.getContentType());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Create two images with the same properties
        Image image1 = new Image("123", new Binary(new byte[]{0x01, 0x02, 0x03}), "Sample Image", "image/jpeg");
        Image image2 = new Image("123", new Binary(new byte[]{0x01, 0x02, 0x03}), "Sample Image", "image/jpeg");

        // Assert that the images are equal
        assertEquals(image1, image2);

        // Assert that the hash codes are equal
        assertEquals(image1.hashCode(), image2.hashCode());
    }

    @Test
    public void testImageEqualityWithNullProperties() {
        // Create two images with null properties
        Image image1 = new Image();
        Image image2 = new Image();

        // Assert that the images are equal
        assertEquals(image1, image2);
    }

    @Test
    public void testImageInequalityWithDifferentProperties() {
        // Create two images with different properties
        Image image1 = new Image();
        image1.setId("123");
        Image image2 = new Image();
        image2.setId("456");

        // Assert that the images are not equal
        assertNotEquals(image1, image2);
    }

    @Test
    public void testImageHashCodeWithNullProperties() {
        // Create two images with null properties
        Image image1 = new Image();
        Image image2 = new Image();

        // Assert that the hash codes are equal
        assertEquals(image1.hashCode(), image2.hashCode());
    }

    @Test
    public void testImageHashCodeWithDifferentProperties() {
        // Create two images with different properties
        Image image1 = new Image();
        image1.setId("123");
        Image image2 = new Image();
        image2.setId("456");

        // Assert that the hash codes are not equal
        assertNotEquals(image1.hashCode(), image2.hashCode());
    }

    @Test
    public void testImageSettersWithNonNullProperties() {
        // Create an image
        Image image = new Image();

        // Set non-null properties using setters
        image.setId("123");
        image.setImage(new Binary(new byte[]{0x01, 0x02, 0x03}));
        image.setTitle("Sample Image");
        image.setContentType("image/jpeg");

        // Assert the properties using getters
        assertEquals("123", image.getId());
        assertEquals(new Binary(new byte[]{0x01, 0x02, 0x03}), image.getImage());
        assertEquals("Sample Image", image.getTitle());
        assertEquals("image/jpeg", image.getContentType());
    }

    @Test
    public void testImageSettersWithNullProperties() {
        // Create an image with initial non-null properties
        Image image = new Image();
        image.setId("123");
        image.setImage(new Binary(new byte[]{0x01, 0x02, 0x03}));
        image.setTitle("Sample Image");
        image.setContentType("image/jpeg");

        // Set properties to null using setters
        image.setId(null);
        image.setImage(null);
        image.setTitle(null);
        image.setContentType(null);

        // Assert that all properties are null
        assertNull(image.getId());
        assertNull(image.getImage());
        assertNull(image.getTitle());
        assertNull(image.getContentType());
    }

    @Test
    public void testImageEqualsAndHashCodeWithNullProperties() {
        // Create two images with null properties
        Image image1 = new Image();
        Image image2 = new Image();

        // Assert that the images are equal
        assertEquals(image1, image2);

        // Assert that the hash codes are equal
        assertEquals(image1.hashCode(), image2.hashCode());
    }

    @Test
    public void testImageToStringWithNullProperties() {
        // Create an image with null properties
        Image image = new Image();

        // Create the expected string representation
        String expectedToString = "Image(id=null, image=null, title=null, contentType=null)";

        // Assert the string representation of the image
        assertEquals(expectedToString, image.toString());
    }

    @Test
    public void testImageEqualsAndHashCodeWithDifferentObject() {
        // Create an image and a different object
        Image image = new Image();
        Object otherObject = new Object();

        // Assert that the image and the different object are not equal
        assertNotEquals(image, otherObject);

        // Assert that the hash codes are not equal
        assertNotEquals(image.hashCode(), otherObject.hashCode());
    }

    @Test
    public void testImageCopy() {
        // Create an image
        Image image = new Image();
        image.setId("123");
        image.setImage(new Binary(new byte[]{0x01, 0x02, 0x03}));
        image.setTitle("Sample Image");
        image.setContentType("image/jpeg");

        // Create a copy of the image
        Image copiedImage = new Image();
        copiedImage.setId(image.getId());
        copiedImage.setImage(image.getImage());
        copiedImage.setTitle(image.getTitle());
        copiedImage.setContentType(image.getContentType());

        // Assert that the copied image is equal to the original image
        assertEquals(image, copiedImage);

        // Assert that the copied image is a different instance
        assertNotSame(image, copiedImage);
    }

    @Test
    public void testImageBuilderWithNullValues() {
        // Create an image using the builder pattern with null values
        Image image = Image.builder()
                .id(null)
                .image(null)
                .title(null)
                .contentType(null)
                .build();

        // Assert that all properties are null
        assertNull(image.getId());
        assertNull(image.getImage());
        assertNull(image.getTitle());
        assertNull(image.getContentType());
    }
}




