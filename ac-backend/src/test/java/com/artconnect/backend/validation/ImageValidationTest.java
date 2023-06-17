package com.artconnect.backend.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class ImageValidationTest {
    private ImageValidation imageValidation;

    @BeforeEach
    void setUp() {
        imageValidation = ImageValidation.builder().build();
    }
    @Test
    void testValidFileWithValidExtensionAndSize() {
        ImageValidation imageValidation = ImageValidation.builder()
                .size(5242880) // 5 MB
                .contentType("image/png")
                .fileName("image.png")
                .build();

        Assertions.assertTrue(imageValidation.validFile());
    }

    @Test
    void testValidFileWithInvalidExtension() {
        ImageValidation imageValidation = ImageValidation.builder()
                .size(5242880) // 5 MB
                .contentType("image/png")
                .fileName("image.txt")
                .build();

        Assertions.assertFalse(imageValidation.validFile());
    }

    @Test
    void testValidFileWithInvalidSize() {
        ImageValidation imageValidation = ImageValidation.builder()
                .size(15728640) // 15 MB
                .contentType("image/png")
                .fileName("image.png")
                .build();

        Assertions.assertFalse(imageValidation.validFile());
    }

    @Test
    void testValidFileWithInvalidExtensionAndSize() {
        ImageValidation imageValidation = ImageValidation.builder()
                .size(15728640) // 15 MB
                .contentType("image/png")
                .fileName("image.txt")
                .build();

        Assertions.assertFalse(imageValidation.validFile());
    }

    @Test
    void testValidFileWithLowerCaseExtension() {
        ImageValidation imageValidation = ImageValidation.builder()
                .size(5242880) // 5 MB
                .contentType("image/png")
                .fileName("image.PNG")
                .build();

        Assertions.assertTrue(imageValidation.validFile());
    }

    @Test
    void testValidFileWithUpperCaseExtension() {
        ImageValidation imageValidation = ImageValidation.builder()
                .size(5242880) // 5 MB
                .contentType("image/png")
                .fileName("image.JPG")
                .build();

        Assertions.assertTrue(imageValidation.validFile());
    }

    @Test
    void testValidFileWithUnsupportedExtension() {
        ImageValidation imageValidation = ImageValidation.builder()
                .size(5242880) // 5 MB
                .contentType("image/png")
                .fileName("image.gif")
                .build();

        Assertions.assertFalse(imageValidation.validFile());
    }

    @Test
    void testValidFileWithExactMaxSize() {
        ImageValidation imageValidation = ImageValidation.builder()
                .size(10485760) // 10 MB
                .contentType("image/png")
                .fileName("image.png")
                .build();

        Assertions.assertTrue(imageValidation.validFile());
    }

    @Test
    void testValidFileWithGreaterThanMaxSize() {
        ImageValidation imageValidation = ImageValidation.builder()
                .size(15728640) // 15 MB
                .contentType("image/png")
                .fileName("image.png")
                .build();

        Assertions.assertFalse(imageValidation.validFile());
    }

    @Test
    void testValidFileWithEmptyFileName() {
        ImageValidation imageValidation = ImageValidation.builder()
                .size(5242880) // 5 MB
                .contentType("image/png")
                .fileName("")
                .build();

        Assertions.assertFalse(imageValidation.validFile());
    }

    @Test
    void testValidFileWithContentTypeWithoutImagePrefix() {
        ImageValidation imageValidation = ImageValidation.builder()
                .size(5242880) // 5 MB
                .contentType("png")
                .fileName("image.png")
                .build();

        Assertions.assertFalse(imageValidation.validFile());
    }

    @Test
    void testGetterMethods() {
        long expectedSize = 5242880;
        String expectedContentType = "image/png";
        String expectedFileName = "image.png";

        ImageValidation imageValidation = ImageValidation.builder()
                .size(expectedSize)
                .contentType(expectedContentType)
                .fileName(expectedFileName)
                .build();

        Assertions.assertEquals(expectedSize, imageValidation.getSize());
        Assertions.assertEquals(expectedContentType, imageValidation.getContentType());
        Assertions.assertEquals(expectedFileName, imageValidation.getFileName());
    }

    @Test
    void testBuilderWithModifiedFields() {
        long initialSize = 5242880;
        String initialContentType = "image/png";
        String initialFileName = "image.png";

        long modifiedSize = 15728640;
        String modifiedContentType = "image/jpeg";
        String modifiedFileName = "picture.jpg";

        ImageValidation imageValidation = ImageValidation.builder()
                .size(initialSize)
                .contentType(initialContentType)
                .fileName(initialFileName)
                .build();

        ImageValidation modifiedValidation = ImageValidation.builder()
                .size(modifiedSize)
                .contentType(modifiedContentType)
                .fileName(modifiedFileName)
                .build();

        Assertions.assertEquals(initialSize, imageValidation.getSize());
        Assertions.assertEquals(initialContentType, imageValidation.getContentType());
        Assertions.assertEquals(initialFileName, imageValidation.getFileName());

        Assertions.assertEquals(modifiedSize, modifiedValidation.getSize());
        Assertions.assertEquals(modifiedContentType, modifiedValidation.getContentType());
        Assertions.assertEquals(modifiedFileName, modifiedValidation.getFileName());
    }

    @Test
    void testToStringMethod() {
        ImageValidation imageValidation = ImageValidation.builder()
                .size(5242880)
                .contentType("image/png")
                .fileName("image.png")
                .build();

        String expected = "ImageValidation(size=5242880, contentType=image/png, fileName=image.png)";
        String actual = imageValidation.toString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testValidFileWithMaxSizeAndEmptyContentTypeAndFileName() {
        ImageValidation imageValidation = ImageValidation.builder()
                .size(10485760) // 10 MB
                .contentType("")
                .fileName("")
                .build();

        Assertions.assertFalse(imageValidation.validFile());
    }

    @Test
    void testValidFileWithNegativeSize() {
        ImageValidation imageValidation = ImageValidation.builder()
                .size(-5242880)
                .contentType("image/png")
                .fileName("image.png")
                .build();

        Assertions.assertTrue(imageValidation.validFile());
    }


    @Test
    void testValidFileWithEmptyContentType() {
        ImageValidation imageValidation = ImageValidation.builder()
                .size(5242880)
                .contentType("")
                .fileName("image.png")
                .build();

        Assertions.assertFalse(imageValidation.validFile());
    }



    @Test
    void testValidFileWithMultipleDotsInFileName() {
        ImageValidation imageValidation = ImageValidation.builder()
                .size(5242880)
                .contentType("image/png")
                .fileName("image.test.png")
                .build();

        Assertions.assertTrue(imageValidation.validFile());
    }

    @Test
    void testEqualsAndHashCodeMethods() {
        ImageValidation imageValidation1 = ImageValidation.builder()
                .size(5242880)
                .contentType("image/png")
                .fileName("image.png")
                .build();

        ImageValidation imageValidation2 = ImageValidation.builder()
                .size(5242880)
                .contentType("image/png")
                .fileName("image.png")
                .build();

        Assertions.assertEquals(imageValidation1, imageValidation2);
        Assertions.assertEquals(imageValidation1.hashCode(), imageValidation2.hashCode());
    }

    @Test
    void testNoArgsConstructor() {
        Assertions.assertEquals(0, imageValidation.getSize());
        Assertions.assertNull(imageValidation.getContentType());
        Assertions.assertNull(imageValidation.getFileName());
    }

    @Test
    void testAllArgsConstructor() {
        long expectedSize = 5242880;
        String expectedContentType = "image/png";
        String expectedFileName = "image.png";

        ImageValidation imageValidation = new ImageValidation(expectedSize, expectedContentType, expectedFileName);

        Assertions.assertEquals(expectedSize, imageValidation.getSize());
        Assertions.assertEquals(expectedContentType, imageValidation.getContentType());
        Assertions.assertEquals(expectedFileName, imageValidation.getFileName());
    }

    @Test
    void testSetterMethods() {
        long expectedSize = 5242880;
        String expectedContentType = "image/png";
        String expectedFileName = "image.png";

        ImageValidation imageValidation = ImageValidation.builder()
                .size(expectedSize)
                .contentType(expectedContentType)
                .fileName(expectedFileName)
                .build();

        Assertions.assertEquals(expectedSize, imageValidation.getSize());
        Assertions.assertEquals(expectedContentType, imageValidation.getContentType());
        Assertions.assertEquals(expectedFileName, imageValidation.getFileName());
    }


    @Test
    void testValidFileWithValidProperties() {
        ImageValidation imageValidation = new ImageValidation(5242880, "image/png", "image.png");

        Assertions.assertTrue(imageValidation.validFile());
    }

}
