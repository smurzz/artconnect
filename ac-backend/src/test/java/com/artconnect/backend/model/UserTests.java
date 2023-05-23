package com.artconnect.backend.model;


import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class UserTests {

    @Nested
    @DisplayName("getAuthorities method")
    class GetAuthorities {

        @Test
        @DisplayName("should return user authorities based on the assigned role")
        void testGetAuthorities() {
            // Create a user with a role
            Role role = Role.ADMIN;  // Replace with the appropriate role constant from your implementation
            User user = User.builder()
                    .role(role)
                    .build();

            // Verify that the user's authorities match the role's authorities
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            assertEquals(role.getAuthorities(), authorities);
        }
    }

    @Nested
    @DisplayName("UserDetails methods")
    class UserDetailsMethods {

        @Test
        @DisplayName("isAccountNonExpired should always return true")
        void testIsAccountNonExpired() {
            User user = new User();
            assertTrue(user.isAccountNonExpired());
        }

        @Test
        @DisplayName("isAccountNonLocked should always return true")
        void testIsAccountNonLocked() {
            User user = new User();
            assertTrue(user.isAccountNonLocked());
        }

        @Test
        @DisplayName("isCredentialsNonExpired should always return true")
        void testIsCredentialsNonExpired() {
            User user = new User();
            assertTrue(user.isCredentialsNonExpired());
        }

        @Test
        @DisplayName("isEnabled should return the correct enabled status")
        void testIsEnabled() {
            User user = User.builder()
                    .isEnabled(true) // Set isEnabled explicitly
                    .build();
            assertTrue(user.isEnabled());
        }
    }

    @Nested
    @DisplayName("Getter methods")
    class GetterMethods {

        @Test
        @DisplayName("getPassword should return the correct password")
        void testGetPassword() {
            // Create a User object with a password
            String expectedPassword = "myPassword";
            User user = User.builder()
                    .password(expectedPassword)
                    .build();

            // Call the getPassword() method and assert the result
            String actualPassword = user.getPassword();
            assertEquals(expectedPassword, actualPassword);
        }

        @Test
        @DisplayName("getUsername should return the correct username (email)")
        void testGetUsername() {
            // Create a User object with an email
            String expectedEmail = "user@example.com";
            User user = User.builder()
                    .email(expectedEmail)
                    .build();

            // Call the getUsername() method and assert the result
            String actualEmail = user.getUsername();
            assertEquals(expectedEmail, actualEmail);
        }
    }

    @Nested
    @DisplayName("Setter methods")
    class SetterMethods {

        @Test
        @DisplayName("setId should set the correct ID")
        void testSetId() {
            User user = new User();
            String expectedId = "123";

            user.setId(expectedId);

            assertEquals(expectedId, user.getId());
        }

        @Test
        @DisplayName("setFirstname should set the correct firstname")
        void testSetFirstname() {
            User user = new User();
            String expectedFirstname = "John";

            user.setFirstname(expectedFirstname);

            assertEquals(expectedFirstname, user.getFirstname());
        }

        @Test
        @DisplayName("setLastname should set the correct lastname")
        void testSetLastname() {
            User user = new User();
            String expectedLastname = "Doe";

            user.setLastname(expectedLastname);

            assertEquals(expectedLastname, user.getLastname());
        }

        @Test
        @DisplayName("setEmail should set the correct email")
        void testSetEmail() {
            User user = new User();
            String expectedEmail = "john.doe@example.com";

            user.setEmail(expectedEmail);

            assertEquals(expectedEmail, user.getEmail());
        }

        @Test
        @DisplayName("setPassword should set the correct password")
        void testSetPassword() {
            User user = new User();
            String expectedPassword = "password123";

            user.setPassword(expectedPassword);

            assertEquals(expectedPassword, user.getPassword());
        }

        @Test
        @DisplayName("setCreatedAt should set the correct creation date")
        void testSetCreatedAt() {
            User user = new User();
            Date expectedCreatedAt = new Date();

            user.setCreatedAt(expectedCreatedAt);

            assertEquals(expectedCreatedAt, user.getCreatedAt());
        }

        @Test
        @DisplayName("setEnabled should set the correct enabled status")
        void testSetEnabled() {
            User user = new User();
            boolean expectedEnabled = true;

            user.setEnabled(expectedEnabled);

            assertEquals(expectedEnabled, user.isEnabled());
        }

        @Test
        @DisplayName("setRole should set the correct role")
        void testSetRole() {
            User user = new User();
            Role expectedRole = Role.ADMIN;

            user.setRole(expectedRole);

            assertEquals(expectedRole, user.getRole());
        }
    }
}