package com.artconnect.backend.controller.response;


import com.artconnect.backend.controller.response.UserResponse;
import com.artconnect.backend.model.Image;
import com.artconnect.backend.model.user.Contacts;
import com.artconnect.backend.model.user.Exhibition;
import com.artconnect.backend.model.user.SocialMedia;
import com.artconnect.backend.model.user.Status;
import com.artconnect.backend.model.user.User;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserResponseTest {

    @Test
    void testFromUser() {
        // Arrange
        User user = User.builder()
                .id("12345")
                .firstname("John")
                .lastname("Doe")
                .createdAt(new Date())
                .profilePhoto(new Image())
                .biography("Some biography")
                .exhibitions(new ArrayList<>())
                .contacts(new Contacts())
                .socialMedias(new ArrayList<>())
                .isDateOfBirthVisible(Status.PUBLIC)
                .dateOfBirthday(LocalDate.of(1990, 1, 1))
                .build();

        // Act
        UserResponse userResponse = UserResponse.fromUser(user);

        // Assert
        assertNotNull(userResponse);
        assertEquals("12345", userResponse.getId());
        assertEquals("John", userResponse.getFirstname());
        assertEquals("Doe", userResponse.getLastname());
        assertEquals(user.getCreatedAt(), userResponse.getCreatedAt());
        assertEquals(user.getProfilePhoto(), userResponse.getProfilePhoto());
        assertEquals("Some biography", userResponse.getBiography());
        assertEquals(user.getExhibitions(), userResponse.getExhibitions());
        assertEquals(user.getContacts(), userResponse.getContacts());
        assertEquals(user.getSocialMedias(), userResponse.getSocialMedias());
        assertEquals(LocalDate.of(1990, 1, 1), userResponse.getDateOfBirthday());
    }

}
