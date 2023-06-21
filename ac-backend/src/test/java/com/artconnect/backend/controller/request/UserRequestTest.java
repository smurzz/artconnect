package com.artconnect.backend.controller.request;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.artconnect.backend.model.Image;
import com.artconnect.backend.model.user.Contacts;
import com.artconnect.backend.model.user.Exhibition;
import com.artconnect.backend.model.user.Role;
import com.artconnect.backend.model.user.SocialMedia;
import com.artconnect.backend.model.user.Status;
import com.artconnect.backend.controller.request.UserRequest;

public class UserRequestTest {

    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        // Create a sample UserRequest object before each test
        userRequest = new UserRequest();
    }

    @Test
    void testSetAndGetId() {
        // Set the id
        userRequest.setId("123");

        // Verify the id is set correctly
        assertEquals("123", userRequest.getId());
    }

    @Test
    void testSetAndGetFirstname() {
        // Set the firstname
        userRequest.setFirstname("John");

        // Verify the firstname is set correctly
        assertEquals("John", userRequest.getFirstname());
    }

    // Add tests for other properties following a similar pattern

    @Test
    void testSetAndGetExhibitions() {
        // Create a list of exhibitions
        List<Exhibition> exhibitions = new ArrayList<>();
        Exhibition exhibition1 = new Exhibition();
        Exhibition exhibition2 = new Exhibition();
        exhibitions.add(exhibition1);
        exhibitions.add(exhibition2);

        // Set the exhibitions
        userRequest.setExhibitions(exhibitions);

        // Verify the exhibitions are set correctly
        assertEquals(2, userRequest.getExhibitions().size());
        assertEquals(exhibition1, userRequest.getExhibitions().get(0));
        assertEquals(exhibition2, userRequest.getExhibitions().get(1));
    }

    @Test
    void testSetAndGetContacts() {
        // Create a Contacts object
        Contacts contacts = new Contacts();
        // Set properties on the Contacts object if necessary

        // Set the contacts
        userRequest.setContacts(contacts);

        // Verify the contacts are set correctly
        assertNotNull(userRequest.getContacts());
    }

    @Test
    void testSetAndGetSocialMedias() {
        // Create a list of social medias
        List<SocialMedia> socialMedias = new ArrayList<>();
        SocialMedia socialMedia1 = new SocialMedia();
        SocialMedia socialMedia2 = new SocialMedia();
        socialMedias.add(socialMedia1);
        socialMedias.add(socialMedia2);

        // Set the social medias
        userRequest.setSocialMedias(socialMedias);

        // Verify the social medias are set correctly
        assertEquals(2, userRequest.getSocialMedias().size());
        assertEquals(socialMedia1, userRequest.getSocialMedias().get(0));
        assertEquals(socialMedia2, userRequest.getSocialMedias().get(1));
    }
    @Test
    void testSetAndGetLastname() {
        // Set the lastname
        userRequest.setLastname("Doe");

        // Verify the lastname is set correctly
        assertEquals("Doe", userRequest.getLastname());
    }

    @Test
    void testSetAndGetEmail() {
        // Set the email
        userRequest.setEmail("john.doe@example.com");

        // Verify the email is set correctly
        assertEquals("john.doe@example.com", userRequest.getEmail());
    }

    @Test
    void testSetAndGetPassword() {
        // Set the password
        userRequest.setPassword("password123");

        // Verify the password is set correctly
        assertEquals("password123", userRequest.getPassword());
    }

    @Test
    void testSetAndGetDateOfBirthday() {
        // Set the date of birthday
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        userRequest.setDateOfBirthday(dateOfBirth);

        // Verify the date of birthday is set correctly
        assertEquals(dateOfBirth, userRequest.getDateOfBirthday());
    }

    @Test
    void testSetAndGetRole() {
        // Set the role
        userRequest.setRole(Role.ADMIN);

        // Verify the role is set correctly
        assertEquals(Role.ADMIN, userRequest.getRole());
    }

    @Test
    void testSetAndGetProfilePhoto() {
        // Create a sample Image object
        Image profilePhoto = new Image();
        // Set properties on the Image object if necessary

        // Set the profile photo
        userRequest.setProfilePhoto(profilePhoto);

        // Verify the profile photo is set correctly
        assertNotNull(userRequest.getProfilePhoto());
        // Add additional assertions if needed
    }

    @Test
    void testSetAndGetBiography() {
        // Set the biography
        userRequest.setBiography("Lorem ipsum dolor sit amet");

        // Verify the biography is set correctly
        assertEquals("Lorem ipsum dolor sit amet", userRequest.getBiography());
    }
}