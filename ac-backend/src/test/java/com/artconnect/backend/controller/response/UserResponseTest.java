package com.artconnect.backend.controller.response;


import com.artconnect.backend.model.Image;
import com.artconnect.backend.model.user.Contacts;
import com.artconnect.backend.model.user.Status;
import com.artconnect.backend.model.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class UserResponseTest {
    private User user;

    @BeforeEach
    void setUp() {
        // Create a sample User object for testing
        user = new User();
        user.setId("123");
        user.setFirstname("John");
        user.setLastname("Doe");
        user.setCreatedAt(new Date());
        user.setProfilePhoto(new Image());
        user.setBiography("Sample biography");
        user.setExhibitions(new ArrayList<>());
        user.setContacts(new Contacts());
        user.setSocialMedias(new ArrayList<>());
    }

    @Test
    void testFromUser() {
        UserResponse userResponse = UserResponse.fromUser(user);

        // Assert that the UserResponse object is not null
        Assertions.assertNotNull(userResponse);

        // Assert that the UserResponse object contains the correct values
        Assertions.assertEquals(user.getId(), userResponse.getId());
        Assertions.assertEquals(user.getFirstname(), userResponse.getFirstname());
        Assertions.assertEquals(user.getLastname(), userResponse.getLastname());
        Assertions.assertEquals(user.getCreatedAt(), userResponse.getCreatedAt());
        Assertions.assertEquals(user.getProfilePhoto(), userResponse.getProfilePhoto());
        Assertions.assertEquals(user.getBiography(), userResponse.getBiography());
        Assertions.assertEquals(user.getExhibitions(), userResponse.getExhibitions());
        Assertions.assertEquals(user.getContacts(), userResponse.getContacts());
        Assertions.assertEquals(user.getSocialMedias(), userResponse.getSocialMedias());
    }

    @Test
    void testFromUserWithDateOfBirthVisible() {
        user.setDateOfBirthday(LocalDate.of(1990, 1, 1));
        user.setIsDateOfBirthVisible(Status.PUBLIC);

        UserResponse userResponse = UserResponse.fromUser(user);

        // Assert that the UserResponse object contains the correct date of birth
        Assertions.assertEquals(user.getDateOfBirthday(), userResponse.getDateOfBirthday());
    }

    @Test
    void testFromUserWithDateOfBirthNotVisible() {
        user.setDateOfBirthday(LocalDate.of(1990, 1, 1));
        user.setIsDateOfBirthVisible(Status.PRIVATE);

        UserResponse userResponse = UserResponse.fromUser(user);

        // Assert that the UserResponse object's date of birth is null
        Assertions.assertNull(userResponse.getDateOfBirthday());
    }

    @Test
    void testFromUserWithNullDateOfBirthVisibility() {
        user.setDateOfBirthday(LocalDate.of(1990, 1, 1));
        user.setIsDateOfBirthVisible(null);

        UserResponse userResponse = UserResponse.fromUser(user);

        // Assert that the UserResponse object's date of birth is null
        Assertions.assertNull(userResponse.getDateOfBirthday());
    }

    @Test
    void testNoArgsConstructor() {
        UserResponse userResponse = new UserResponse();

        // Assert that the UserResponse object is not null
        Assertions.assertNotNull(userResponse);

        // Assert that the fields in the UserResponse object are null
        Assertions.assertNull(userResponse.getId());
        Assertions.assertNull(userResponse.getFirstname());
        Assertions.assertNull(userResponse.getLastname());
        Assertions.assertNull(userResponse.getDateOfBirthday());
        Assertions.assertNull(userResponse.getCreatedAt());
        Assertions.assertNull(userResponse.getProfilePhoto());
        Assertions.assertNull(userResponse.getBiography());
        Assertions.assertNull(userResponse.getExhibitions());
        Assertions.assertNull(userResponse.getContacts());
        Assertions.assertNull(userResponse.getSocialMedias());
    }

    @Test
    void testSetterMethods() {
        // Create a new UserResponse object
        UserResponse userResponse = new UserResponse();

        // Set values using the setter methods
        userResponse.setId("123");
        userResponse.setFirstname("John");
        userResponse.setLastname("Doe");
        userResponse.setDateOfBirthday(LocalDate.of(1990, 1, 1));
        userResponse.setCreatedAt(new Date());
        userResponse.setProfilePhoto(new Image());
        userResponse.setBiography("Sample biography");
        userResponse.setExhibitions(new ArrayList<>());
        userResponse.setContacts(new Contacts());
        userResponse.setSocialMedias(new ArrayList<>());

        // Assert that the UserResponse object contains the correct values
        Assertions.assertEquals("123", userResponse.getId());
        Assertions.assertEquals("John", userResponse.getFirstname());
        Assertions.assertEquals("Doe", userResponse.getLastname());
        Assertions.assertEquals(LocalDate.of(1990, 1, 1), userResponse.getDateOfBirthday());
        Assertions.assertNotNull(userResponse.getCreatedAt());
        Assertions.assertNotNull(userResponse.getProfilePhoto());
        Assertions.assertEquals("Sample biography", userResponse.getBiography());
        Assertions.assertNotNull(userResponse.getExhibitions());
        Assertions.assertNotNull(userResponse.getContacts());
        Assertions.assertNotNull(userResponse.getSocialMedias());
    }
    @Test
    void testEqualsAndHashCode() {
        UserResponse userResponse1 = UserResponse.fromUser(user);
        UserResponse userResponse2 = UserResponse.fromUser(user);

        // Assert that two UserResponse objects created from the same User have the same hash code
        Assertions.assertEquals(userResponse1.hashCode(), userResponse2.hashCode());

        // Assert that the two UserResponse objects are equal
        Assertions.assertEquals(userResponse1, userResponse2);
    }

    @Test
    void testToString() {
        UserResponse userResponse = UserResponse.fromUser(user);

        // Assert that the UserResponse object has a non-null string representation
        Assertions.assertNotNull(userResponse.toString());
    }
}