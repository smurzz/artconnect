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
}
