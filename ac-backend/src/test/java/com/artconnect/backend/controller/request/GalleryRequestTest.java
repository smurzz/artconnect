package com.artconnect.backend.controller.request;

import com.artconnect.backend.model.gallery.GalleryCategory;
import org.junit.jupiter.api.*;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;
public class GalleryRequestTest {
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @BeforeAll
    public static void setup() {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Test
    public void shouldValidateRequiredFields() {
        GalleryRequest request = new GalleryRequest();
        Set<ConstraintViolation<GalleryRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        ConstraintViolation<GalleryRequest> violation = violations.iterator().next();
        assertEquals("must not be blank", violation.getMessage());
        assertEquals("title", violation.getPropertyPath().toString());
    }

    @Test
    void shouldPassValidation() {
        GalleryRequest request = new GalleryRequest();
        request.setTitle("Sample Title");

        Set<ConstraintViolation<GalleryRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldPassValidationWithArtworkIds() {
        GalleryRequest request = new GalleryRequest();
        request.setTitle("Sample Title");

        List<String> artworkIds = new ArrayList<>();
        artworkIds.add("Artwork 1");
        artworkIds.add("Artwork 2");
        request.setArtworkIds(artworkIds);

        Set<ConstraintViolation<GalleryRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldPassValidationWithEvaluations() {
        GalleryRequest request = new GalleryRequest();
        request.setTitle("Sample Title");

        Map<String, Integer> evaluations = new HashMap<>();
        evaluations.put("User1", 5);
        evaluations.put("User2", 4);
        request.setEvaluations(evaluations);

        Set<ConstraintViolation<GalleryRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

}