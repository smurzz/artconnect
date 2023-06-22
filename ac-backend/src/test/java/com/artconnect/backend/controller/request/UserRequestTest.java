package com.artconnect.backend.controller.request;
import com.artconnect.backend.model.user.*;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRequestTest {

    private UserRequest userRequest;



        @Test
        void testFirstname() {
            String firstname = "John";
            UserRequest userRequest = new UserRequest();
            userRequest.setFirstname(firstname);
            String retrievedFirstname = userRequest.getFirstname();

            assertEquals(firstname, retrievedFirstname);
        }

    @Test
    void testLastname() {
        // Arrange
        String lastname = "Doe";
        UserRequest userRequest = new UserRequest();

        // Act
        userRequest.setLastname(lastname);
        String retrievedLastname = userRequest.getLastname();

        // Assert
        assertEquals(lastname, retrievedLastname);
    }

    @Test
    void testDateOfBirthday() {
        // Arrange
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        UserRequest userRequest = new UserRequest();

        // Act
        userRequest.setDateOfBirthday(dateOfBirth);
        LocalDate retrievedDateOfBirth = userRequest.getDateOfBirthday();

        // Assert
        assertEquals(dateOfBirth, retrievedDateOfBirth);
    }

    @Test
    void testIsDateOfBirthVisible() {
        // Arrange
        Status isDateOfBirthVisible = Status.PUBLIC;
        UserRequest userRequest = new UserRequest();

        // Act
        userRequest.setIsDateOfBirthVisible(isDateOfBirthVisible);
        Status retrievedIsDateOfBirthVisible = userRequest.getIsDateOfBirthVisible();

        // Assert
        assertEquals(isDateOfBirthVisible, retrievedIsDateOfBirthVisible);
    }


    @Test
    void testBiography() {
        // Arrange
        String biography = "Lorem ipsum dolor sit amet.";
        UserRequest userRequest = new UserRequest();

        // Act
        userRequest.setBiography(biography);
        String retrievedBiography = userRequest.getBiography();

        // Assert
        assertEquals(biography, retrievedBiography);
    }


    @Test
    void testExhibitions() {
        // Arrange
        List<Exhibition> exhibitions = Collections.singletonList(new Exhibition());
        UserRequest userRequest = new UserRequest();

        // Act
        userRequest.setExhibitions(exhibitions);
        List<Exhibition> retrievedExhibitions = userRequest.getExhibitions();

        // Assert
        assertEquals(exhibitions, retrievedExhibitions);
    }


    @Test
    void testContacts() {
        // Arrange
        Address address = Address.builder()
                .street("123 Main Street")
                .postalCode("12345")
                .city("City")
                .country("Country")
                .build();

        Contacts contacts = Contacts.builder()
                .telefonNumber(123456789)
                .address(address)
                .website("example.com")
                .build();

        UserRequest userRequest = new UserRequest();

        // Act
        userRequest.setContacts(contacts);
        Contacts retrievedContacts = userRequest.getContacts();

        // Assert
        assertEquals(contacts, retrievedContacts);
        assertEquals(123456789, retrievedContacts.getTelefonNumber());
        assertEquals(address, retrievedContacts.getAddress());
        assertEquals("example.com", retrievedContacts.getWebsite());
    }

    @Test
    void testSocialMedias() {
        // Arrange
        List<SocialMedia> socialMedias = Collections.singletonList(
                SocialMedia.builder()
                        .title("Twitter")
                        .link("twitter.com/user")
                        .build()
        );
        UserRequest userRequest = new UserRequest();

        // Act
        userRequest.setSocialMedias(socialMedias);
        List<SocialMedia> retrievedSocialMedias = userRequest.getSocialMedias();

        // Assert
        assertEquals(socialMedias, retrievedSocialMedias);
        assertEquals("Twitter", retrievedSocialMedias.get(0).getTitle());
        assertEquals("twitter.com/user", retrievedSocialMedias.get(0).getLink());
    }

}

