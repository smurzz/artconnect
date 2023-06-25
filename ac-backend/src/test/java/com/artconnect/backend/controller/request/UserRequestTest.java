package com.artconnect.backend.controller.request;
import static com.mongodb.assertions.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
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
    private UserRequest userRequest1;
    private UserRequest userRequest2;

    @BeforeEach
    void setUp() {
        // Create a sample UserRequest object before each test
        userRequest = new UserRequest();
        userRequest1 = new UserRequest();
        userRequest2 = new UserRequest();
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
    }

    @Test
    void testSetAndGetBiography() {
        // Set the biography
        userRequest.setBiography("Lorem ipsum dolor sit amet");

        // Verify the biography is set correctly
        assertEquals("Lorem ipsum dolor sit amet", userRequest.getBiography());
    }

    @Test
    void isDateOfBirthVisible_DefaultValue_ShouldBeNull() {
        // Assert
        assertNull(userRequest.getIsDateOfBirthVisible());
    }

    @Test
    void isAccountEnabled_DefaultValue_ShouldBeNull() {
        // Assert
        assertNull(userRequest.getIsAccountEnabled());
    }

    @Test
    void testAllArgsConstructor() {
        // Create sample objects for the constructor
        String id = "123";
        String firstname = "John";
        String lastname = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        Status isDateOfBirthVisible = Status.PUBLIC;
        Status isAccountEnabled = Status.PUBLIC; 
        Role role = Role.ADMIN;
        Image profilePhoto = new Image();
        String biography = "Lorem ipsum dolor sit amet";
        List<Exhibition> exhibitions = new ArrayList<>();
        Contacts contacts = new Contacts();
        List<SocialMedia> socialMedias = new ArrayList<>();

        // Create a UserRequest object using the @AllArgsConstructor
        UserRequest userRequest = new UserRequest(id, firstname, lastname, email, password, dateOfBirth,
        		isDateOfBirthVisible, isAccountEnabled, role, profilePhoto, biography, exhibitions, contacts, socialMedias);

        // Verify that the values are set correctly
        assertEquals(id, userRequest.getId());
        assertEquals(firstname, userRequest.getFirstname());
        assertEquals(lastname, userRequest.getLastname());
        assertEquals(email, userRequest.getEmail());
        assertEquals(password, userRequest.getPassword());
        assertEquals(dateOfBirth, userRequest.getDateOfBirthday());
        assertEquals(Status.PUBLIC, userRequest.getIsDateOfBirthVisible());
        assertEquals(Status.PUBLIC, userRequest.getIsAccountEnabled());
        assertEquals(role, userRequest.getRole());
        assertEquals(profilePhoto, userRequest.getProfilePhoto());
        assertEquals(biography, userRequest.getBiography());
        assertEquals(exhibitions, userRequest.getExhibitions());
        assertEquals(contacts, userRequest.getContacts());
        assertEquals(socialMedias, userRequest.getSocialMedias());
    }

    @Test
    void testNoArgsConstructor() {
        // Create a UserRequest object using the no-args constructor
        UserRequest userRequest = new UserRequest();

        // Verify that the object is not null
        assertNotNull(userRequest);
    }

    @Test
    void testSetters() {
        // Create a sample UserRequest object
        UserRequest userRequest = new UserRequest();

        // Use setters to set the values
        String id = "123";
        String firstname = "John";
        String lastname = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        Status isDateOfBirthVisible = Status.PUBLIC;
        Status isAccountEnabled = Status.PUBLIC;
        Role role = Role.ADMIN;
        Image profilePhoto = new Image();
        String biography = "Lorem ipsum dolor sit amet";
        List<Exhibition> exhibitions = new ArrayList<>();
        Contacts contacts = new Contacts();
        List<SocialMedia> socialMedias = new ArrayList<>();

        userRequest.setId(id);
        userRequest.setFirstname(firstname);
        userRequest.setLastname(lastname);
        userRequest.setEmail(email);
        userRequest.setPassword(password);
        userRequest.setDateOfBirthday(dateOfBirth);
        userRequest.setIsDateOfBirthVisible(isDateOfBirthVisible);
        userRequest.setIsAccountEnabled(isAccountEnabled);
        userRequest.setRole(role);
        userRequest.setProfilePhoto(profilePhoto);
        userRequest.setBiography(biography);
        userRequest.setExhibitions(exhibitions);
        userRequest.setContacts(contacts);
        userRequest.setSocialMedias(socialMedias);

        // Verify that the values are set correctly
        assertEquals(id, userRequest.getId());
        assertEquals(firstname, userRequest.getFirstname());
        assertEquals(lastname, userRequest.getLastname());
        assertEquals(email, userRequest.getEmail());
        assertEquals(password, userRequest.getPassword());
        assertEquals(dateOfBirth, userRequest.getDateOfBirthday());
        assertEquals(isDateOfBirthVisible, userRequest.getIsDateOfBirthVisible());
        assertEquals(isAccountEnabled, userRequest.getIsAccountEnabled());
        assertEquals(role, userRequest.getRole());
        assertSame(profilePhoto, userRequest.getProfilePhoto());
        assertEquals(biography, userRequest.getBiography());
        assertSame(exhibitions, userRequest.getExhibitions());
        assertSame(contacts, userRequest.getContacts());
        assertSame(socialMedias, userRequest.getSocialMedias());
    }


    @Test
    void testSettersAndGetters() {
        String id = "123";
        String firstname = "John";
        String lastname = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        Status isDateOfBirthVisible = Status.PUBLIC;
        Status isAccountEnabled = Status.PUBLIC;
        Role role = Role.ADMIN;
        Image profilePhoto = new Image();
        String biography = "Lorem ipsum dolor sit amet";
        List<Exhibition> exhibitions = new ArrayList<>();
        Contacts contacts = new Contacts();
        List<SocialMedia> socialMedias = new ArrayList<>();

        // Use setters to set the values
        userRequest.setId(id);
        userRequest.setFirstname(firstname);
        userRequest.setLastname(lastname);
        userRequest.setEmail(email);
        userRequest.setPassword(password);
        userRequest.setDateOfBirthday(dateOfBirth);
        userRequest.setIsDateOfBirthVisible(isDateOfBirthVisible);
        userRequest.setIsAccountEnabled(isAccountEnabled);
        userRequest.setRole(role);
        userRequest.setProfilePhoto(profilePhoto);
        userRequest.setBiography(biography);
        userRequest.setExhibitions(exhibitions);
        userRequest.setContacts(contacts);
        userRequest.setSocialMedias(socialMedias);

        // Verify that the values are set correctly using getters
        assertEquals(id, userRequest.getId());
        assertEquals(firstname, userRequest.getFirstname());
        assertEquals(lastname, userRequest.getLastname());
        assertEquals(email, userRequest.getEmail());
        assertEquals(password, userRequest.getPassword());
        assertEquals(dateOfBirth, userRequest.getDateOfBirthday());
        assertEquals(isDateOfBirthVisible, userRequest.getIsDateOfBirthVisible());
        assertEquals(isAccountEnabled, userRequest.getIsAccountEnabled());
        assertEquals(role, userRequest.getRole());
        assertSame(profilePhoto, userRequest.getProfilePhoto());
        assertEquals(biography, userRequest.getBiography());
        assertSame(exhibitions, userRequest.getExhibitions());
        assertSame(contacts, userRequest.getContacts());
        assertSame(socialMedias, userRequest.getSocialMedias());
    }

    @Test
    void testSetAndGetExhibitions_WhenExhibitionsIsNull_ShouldSetAndGetExhibitionsCorrectly() {
        // Set the exhibitions to null
        userRequest.setExhibitions(null);

        // Verify that the exhibitions are set correctly
        assertNull(userRequest.getExhibitions());
    }

    @Test
    void testSetAndGetSocialMedias_WhenSocialMediasIsNull_ShouldSetAndGetSocialMediasCorrectly() {
        // Set the social medias to null
        userRequest.setSocialMedias(null);

        // Verify that the social medias are set correctly
        assertNull(userRequest.getSocialMedias());
    }

    @Test
    void testEquals_WhenObjectsAreNotEqual_ShouldReturnFalse() {
        // Set different values for the UserRequest objects
        userRequest1.setId("123");
        userRequest2.setId("456");

        // Verify that the objects are not considered equal
        assertNotEquals(userRequest1, userRequest2);
    }

    @Test
    void testHashCode_WhenObjectsAreNotEqual_ShouldReturnDifferentHashCode() {
        // Set different values for the UserRequest objects
        userRequest1.setId("123");
        userRequest2.setId("456");

        // Verify that the hash codes are different
        assertNotEquals(userRequest1.hashCode(), userRequest2.hashCode());
    }

    @Test
    void testNoArgsConstructor_WhenCalled_ShouldSetFieldsToNull() {
        // Verify that the no-args constructor sets all fields to null
        assertNull(userRequest1.getId());
        assertNull(userRequest1.getFirstname());
        assertNull(userRequest1.getLastname());
    }

    @Test
    void testNoArgsConstructor_WhenAllFieldsProvided_ShouldSetFields() {
        // Create a new UserRequest object using the no-args constructor
        UserRequest userRequest = new UserRequest();

        // Set the fields using setter methods
        userRequest.setId("123");
        userRequest.setFirstname("John");
        userRequest.setLastname("Doe");

        // Verify that the fields are set correctly
        assertEquals("123", userRequest.getId());
        assertEquals("John", userRequest.getFirstname());
        assertEquals("Doe", userRequest.getLastname());
    }

    @Test
    void testEquals_WhenSameObject_ShouldReturnTrue() {
        // Verify that the object is considered equal to itself
        assertEquals(userRequest, userRequest);
    }

    @Test
    void testEquals_WhenNullObject_ShouldReturnFalse() {
        // Verify that the object is not considered equal to null
        assertFalse(userRequest.equals(null));
    }

    @Test
    void testSetterMethods_WhenSomeFieldsNull_ShouldSetFieldsToNull() {
        // Create a new UserRequest object
        UserRequest userRequest = new UserRequest();

        // Set the fields using setter methods
        userRequest.setId("123");
        userRequest.setFirstname(null);
        userRequest.setLastname("Doe");

        // Verify that the null fields are set correctly
        assertEquals("123", userRequest.getId());
        assertNull(userRequest.getFirstname());
        assertEquals("Doe", userRequest.getLastname());
    }

    @Test
    void testNoArgsConstructor_WhenCalled_ShouldCreateObject() {
        // Verify that the no-args constructor creates a non-null object
        assertNotNull(userRequest);
    }

    @Test
    void testNoArgsConstructor_WhenCalled_ShouldSetFieldsToDefaultValues() {
        // Create a UserRequest object using the no-args constructor
        UserRequest userRequest = new UserRequest();

        // Verify that the fields are set to their default values
        assertNull(userRequest.getId());
        assertNull(userRequest.getFirstname());
        assertNull(userRequest.getLastname());
        assertNull(userRequest.getEmail());
        assertNull(userRequest.getPassword());
        assertNull(userRequest.getDateOfBirthday());
        assertNull(userRequest.getIsDateOfBirthVisible());
        assertNull(userRequest.getIsAccountEnabled());
        assertNull(userRequest.getRole());
        assertNull(userRequest.getProfilePhoto());
        assertNull(userRequest.getBiography());
        assertNull(userRequest.getExhibitions());
        assertNull(userRequest.getContacts());
        assertNull(userRequest.getSocialMedias());
    }
    @Test
    void testHashCode_WhenObjectsAreNotEqual_ShouldReturnDifferentHashCodes() {
        // Create two UserRequest objects with different values
        UserRequest userRequest1 = new UserRequest();
        UserRequest userRequest2 = new UserRequest();

        userRequest1.setId("123");
        userRequest2.setId("456");
        userRequest1.setFirstname("John");
        userRequest2.setFirstname("Jane");
        userRequest1.setLastname("Doe");
        userRequest2.setLastname("Smith");

        // Verify that the hash codes are different
        assertNotEquals(userRequest1.hashCode(), userRequest2.hashCode());
    }

    @Test
    void testNoArgsConstructor_ShouldCreateObjectWithDefaultValues() {
        // Create a UserRequest object using the no-args constructor
        UserRequest userRequest = new UserRequest();

        // Verify that the object is not null and has default values
        assertNotNull(userRequest);
        assertNull(userRequest.getId());
        assertNull(userRequest.getFirstname());
        assertNull(userRequest.getLastname());
    }
    @Test
    void testSetterAndGetters_ShouldSetAndRetrievePropertyValues() {
        // Create a UserRequest object
        UserRequest userRequest = new UserRequest();

        // Set property values using setters
        userRequest.setId("123");
        userRequest.setFirstname("John");
        userRequest.setLastname("Doe");

        // Verify that the property values are correctly set
        assertEquals("123", userRequest.getId());
        assertEquals("John", userRequest.getFirstname());
        assertEquals("Doe", userRequest.getLastname());
    }

    @Test
    void testNoArgsConstructor_ShouldInitializeFieldsWithDefaultValues() {
        // Act
        UserRequest userRequest = new UserRequest();

        // Assert
        assertNull(userRequest.getId());
        assertNull(userRequest.getFirstname());
        assertNull(userRequest.getLastname());
        assertNull(userRequest.getEmail());
        assertNull(userRequest.getPassword());
        assertNull(userRequest.getDateOfBirthday());
        assertNull(userRequest.getIsDateOfBirthVisible());
        assertNull(userRequest.getIsAccountEnabled());
        assertNull(userRequest.getRole());
        assertNull(userRequest.getProfilePhoto());
        assertNull(userRequest.getBiography());
        assertNull(userRequest.getExhibitions());
        assertNull(userRequest.getContacts());
        assertNull(userRequest.getSocialMedias());
    }
}