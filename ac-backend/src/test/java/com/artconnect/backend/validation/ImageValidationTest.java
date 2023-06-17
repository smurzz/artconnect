package com.artconnect.backend.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ImageValidationTest {

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
}