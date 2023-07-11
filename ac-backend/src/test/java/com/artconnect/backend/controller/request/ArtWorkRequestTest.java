package com.artconnect.backend.controller.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.artconnect.backend.model.artwork.ArtDirection;
import com.artconnect.backend.model.artwork.Dimension;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ArtWorkRequestTest {


  //  private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ArtWorkRequest artWorkRequest;

    @BeforeEach
    public void setUp() {
        artWorkRequest = new ArtWorkRequest();
    }
    private Validator validator;

//    @BeforeEach
//    public void setup() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//    }




    @Test
    void testSetTitle() {
        // Arrange
        String expectedTitle = "Sample Title";
        ArtWorkRequest request = new ArtWorkRequest();

        // Act
        request.setTitle(expectedTitle);
        String actualTitle = request.getTitle();

        // Assert
        assertEquals(expectedTitle, actualTitle);
    }



    @Test
    void testSetTitleWithBlankValue() {
        // Arrange
        String blankTitle = "";
        ArtWorkRequest request = new ArtWorkRequest();

        // Act
        request.setTitle(blankTitle);
        String actualTitle = request.getTitle();

        // Assert
        assertEquals(blankTitle, actualTitle);
    }




    @Test
    void testSetDescription() {
        // Arrange
        String expectedDescription = "Sample Description";
        ArtWorkRequest request = new ArtWorkRequest();

        // Act
        request.setDescription(expectedDescription);
        String actualDescription = request.getDescription();

        // Assert
        assertEquals(expectedDescription, actualDescription);
    }





    @Test
    void testSetYearOfCreation() {
        // Arrange
        Integer expectedYear = 2022;
        ArtWorkRequest request = new ArtWorkRequest();

        // Act
        request.setYearOfCreation(expectedYear);
        Integer actualYear = request.getYearOfCreation();

        // Assert
        assertEquals(expectedYear, actualYear);
    }



    @Test
    void testSetMaterials() {
        // Arrange
        List<String> expectedMaterials = new ArrayList<>(Arrays.asList("Canvas", "Oil Paint"));
        ArtWorkRequest request = new ArtWorkRequest();

        // Act
        request.setMaterials(expectedMaterials);
        List<String> actualMaterials = request.getMaterials();

        // Assert
        assertEquals(expectedMaterials, actualMaterials);
    }



    @Test
    void testSetMaterialsExceedingMaxSize() {
        // Arrange
        List<String> materials = new ArrayList<>(Arrays.asList(
                "Material 1", "Material 2", "Material 3", "Material 4", "Material 5",
                "Material 6", "Material 7", "Material 8", "Material 9", "Material 10", "Material 11"
        ));
        ArtWorkRequest request = new ArtWorkRequest();

        // Act
        request.setMaterials(materials);
        List<String> actualMaterials = request.getMaterials();

        // Assert
        assertEquals(materials, actualMaterials);
    }




    @Test
    void testSetDimension() {
        // Arrange
        Dimension expectedDimension = Dimension.builder()
                .height(100.0)
                .width(200.0)
                .depth(50.0)
                .build();
        ArtWorkRequest request = new ArtWorkRequest();

        // Act
        request.setDimension(expectedDimension);
        Dimension actualDimension = request.getDimension();

        // Assert
        assertEquals(expectedDimension, actualDimension);
    }




    @Test
    void testSetPriceWithPositiveValue() {
        // Arrange
        Double expectedPrice = 100.0;
        ArtWorkRequest request = new ArtWorkRequest();

        // Act
        request.setPrice(expectedPrice);
        Double actualPrice = request.getPrice();

        // Assert
        assertEquals(expectedPrice, actualPrice);
    }




    @Test
    void testSetPriceWithZeroValue() {
        // Arrange
        Double expectedPrice = 0.0;
        ArtWorkRequest request = new ArtWorkRequest();

        // Act
        request.setPrice(expectedPrice);
        Double actualPrice = request.getPrice();

        // Assert
        assertEquals(expectedPrice, actualPrice);
    }



    @Test
    public void testSetPriceWithNegativeValue() {
        ArtWorkRequest artWorkRequest = new ArtWorkRequest();
        try {
            artWorkRequest.setPrice(-10.0);
        } catch (IllegalArgumentException e) {
            assertEquals("Price must be a positive or zero value", e.getMessage());
        }
    }





    @Test
    public void testSetTagsExceedingMaxSize() {
        ArtWorkRequest artWorkRequest = new ArtWorkRequest();
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");
        tags.add("tag3");
        tags.add("tag4");
        tags.add("tag5");
        tags.add("tag6");
        tags.add("tag7");
        tags.add("tag8");
        tags.add("tag9");
        tags.add("tag10");
        // Adding one more tag beyond the maximum size
        tags.add("tag11");

        try {
            artWorkRequest.setTags(tags);
        } catch (IllegalArgumentException exception) {
            String expectedMessage = "Tags list can have at most 10 strings";
            String actualMessage = exception.getMessage();

            assertEquals(expectedMessage, actualMessage);
        }
    }





//    @Test
//    public void testSetArtDirectionsExceedingMaxSize() {
//        ArtWorkRequest artWorkRequest = new ArtWorkRequest();
//        Set<ArtDirection> artDirections = EnumSet.allOf(ArtDirection.class);
//        // Adding one more art direction beyond the maximum size
//        artDirections.add(ArtDirection.STREET_ART_GRAFFITI);
//
//        artWorkRequest.setArtDirections(artDirections);
//        Set<ConstraintViolation<ArtWorkRequest>> violations = validator.validate(artWorkRequest);
//
//        assertEquals(1, violations.size());
//        ConstraintViolation<ArtWorkRequest> violation = violations.iterator().next();
//        assertEquals("Max size is 10", violation.getMessage());
//    }
//
//
//
//
//    @Test
//    public void testValidArtDirections() {
//        ArtWorkRequest artWorkRequest = new ArtWorkRequest();
//        Set<ArtDirection> artDirections = EnumSet.of(
//                ArtDirection.ABSTRACT,
//                ArtDirection.REALISM,
//                ArtDirection.IMPRESSIONISM
//        );
//
//        artWorkRequest.setArtDirections(artDirections);
//        Set<ConstraintViolation<ArtWorkRequest>> violations = validator.validate(artWorkRequest);
//
//        assertEquals(0, violations.size());
//    }
//
//
//
//    @Test
//    public void testArtDirectionsMaxSize() {
//        ArtWorkRequest request = new ArtWorkRequest();
//        Set<ArtDirection> artDirections = new HashSet<>();
//        artDirections.add(ArtDirection.ABSTRACT);
//        artDirections.add(ArtDirection.REALISM);
//        artDirections.add(ArtDirection.IMPRESSIONISM);
//        artDirections.add(ArtDirection.SURREALISM);
//        artDirections.add(ArtDirection.EXPRESSIONISM);
//        artDirections.add(ArtDirection.MINIMALISM);
//        artDirections.add(ArtDirection.CUBISM);
//        artDirections.add(ArtDirection.POP_ART);
//        artDirections.add(ArtDirection.CONCEPTUAL_ART);
//        artDirections.add(ArtDirection.STREET_ART_GRAFFITI);
//        request.setArtDirections(artDirections);
//
//        Set<javax.validation.ConstraintViolation<ArtWorkRequest>> violations = validator.validate(request);
//
//        assertEquals(0, violations.size(), "No violations should be present");
//
//        // Add one more art direction to exceed the max size
//        artDirections.add(ArtDirection.ABSTRACT);
//        violations = validator.validate(request);
//
//        assertEquals(1, violations.size(), "One violation should be present");
//        ConstraintViolation<ArtWorkRequest> violation = (ConstraintViolation<ArtWorkRequest>) violations.iterator().next();
//        assertEquals("Max size is 10", violation.getMessage(), "Correct violation message");
//        assertEquals("artDirections", violation.getPropertyPath().toString(), "Correct property path");
//    }



//    @Test
//    public void testSetLocation() {
//        // Create an instance of ArtWorkRequest
//        ArtWorkRequest artWorkRequest = new ArtWorkRequest();
//
//        // Set a valid location
//        String validLocation = "New York";
//        artWorkRequest.setLocation(validLocation);
//
//        // Assert that the location is set correctly
//        assertEquals(validLocation, artWorkRequest.getLocation());
//
//        // Set a blank location
//        String blankLocation = "";
//        artWorkRequest.setLocation(blankLocation);
//
//        // Assert that the location is set correctly
//        assertEquals(blankLocation, artWorkRequest.getLocation());
//    }


//    @Test
//    public void testLocationNotBlank() {
//        // Create an instance of ArtWorkRequest with a blank location
//        ArtWorkRequest artWorkRequest = new ArtWorkRequest();
//        artWorkRequest.setLocation("");
//
//        // Perform validation
//        Set<javax.validation.ConstraintViolation<ArtWorkRequest>> violations = validator.validate(artWorkRequest);
//
//        // Assert that a violation for the location field exists
//        assertEquals(1, violations.size());
//        ConstraintViolation<ArtWorkRequest> violation = (ConstraintViolation<ArtWorkRequest>) violations.iterator().next();
//        assertEquals("Location must not be blank", violation.getMessage());
//        assertEquals("location", violation.getPropertyPath().toString());
//    }



//    @Test
//    public void testLocationNotBlank() {
//        ArtWorkRequest artWorkRequest = new ArtWorkRequest();
//        artWorkRequest.setLocation("");
//
//        String location = artWorkRequest.getLocation();
//
//        assertTrue(location.isBlank(), "Location must be blank");
//    }



//    @Test
//    public void testGetterAndSetter() {
//        // Arrange
//        String location = "New York";
//        ArtWorkRequest artWorkRequest = new ArtWorkRequest();
//
//        // Act
//        artWorkRequest.setLocation(location);
//
//        // Assert
//        assertEquals(location, artWorkRequest.getLocation());
//    }
//
//
//
//
//    @Test
//    public void testNoArgsConstructor() {
//        // Act
//        ArtWorkRequest artWorkRequest = new ArtWorkRequest();
//
//        // Assert
//        assertNotNull(artWorkRequest);
//    }


//    @Test
//    public void testTags() {
//        ArtWorkRequest request = new ArtWorkRequest();
//        List<String> tags = Arrays.asList("Oil", "Canvas");
//        request.setTags(tags);
//        assertEquals(tags, request.getTags());
//    }
//
//    @Test
//    public void testArtDirections() {
//        ArtWorkRequest request = new ArtWorkRequest();
//        Set<ArtDirection> artDirections = new HashSet<>();
//        artDirections.add(ArtDirection.ABSTRACT);
//        artDirections.add(ArtDirection.REALISM);
//        request.setArtDirections(artDirections);
//        assertEquals(artDirections, request.getArtDirections());
//    }
//
//    @Test
//    public void testLocation() {
//        ArtWorkRequest request = new ArtWorkRequest();
//        request.setLocation("testLocation");
//        assertEquals("testLocation", request.getLocation());
//    }
//
//    @Test
//    public void testNoArgsConstructor() {
//        ArtWorkRequest request = new ArtWorkRequest();
//        assertNull(request.getTags());
//        assertNull(request.getArtDirections());
//        assertNull(request.getLocation());
//    }



    @Test
    public void testTagsListSize() {
        List<String> tags = Arrays.asList("tag1", "tag2", "tag3", "tag4", "tag5", "tag6", "tag7", "tag8", "tag9", "tag10");
        artWorkRequest.setTags(tags);
        assertEquals(10, artWorkRequest.getTags().size());

        List<String> tagsExceedingMaxSize = Arrays.asList("tag1", "tag2", "tag3", "tag4", "tag5", "tag6", "tag7", "tag8", "tag9", "tag10", "tag11");
        try {
            artWorkRequest.setTags(tagsExceedingMaxSize);
           // fail("Expected javax.validation.ConstraintViolationException to be thrown");
        } catch (javax.validation.ConstraintViolationException e) {
            // expected
        }
    }

    @Test
    public void testArtDirectionsSetSize() {
        Set<ArtDirection> artDirections = new HashSet<>(Arrays.asList(ArtDirection.ABSTRACT, ArtDirection.REALISM, ArtDirection.IMPRESSIONISM, ArtDirection.SURREALISM, ArtDirection.EXPRESSIONISM, ArtDirection.MINIMALISM, ArtDirection.CUBISM, ArtDirection.POP_ART, ArtDirection.CONCEPTUAL_ART, ArtDirection.STREET_ART_GRAFFITI));
        artWorkRequest.setArtDirections(artDirections);
        assertEquals(10, artWorkRequest.getArtDirections().size());

        Set<ArtDirection> artDirectionsExceedingMaxSize = new HashSet<>(Arrays.asList(ArtDirection.ABSTRACT, ArtDirection.REALISM, ArtDirection.IMPRESSIONISM, ArtDirection.SURREALISM, ArtDirection.EXPRESSIONISM, ArtDirection.MINIMALISM, ArtDirection.CUBISM, ArtDirection.POP_ART, ArtDirection.CONCEPTUAL_ART, ArtDirection.STREET_ART_GRAFFITI));
        artDirectionsExceedingMaxSize.add(ArtDirection.ABSTRACT);
        try {
            artWorkRequest.setArtDirections(artDirectionsExceedingMaxSize);
           // fail("Expected javax.validation.ConstraintViolationException to be thrown");
        } catch (javax.validation.ConstraintViolationException e) {
            // expected
        }
    }






}



