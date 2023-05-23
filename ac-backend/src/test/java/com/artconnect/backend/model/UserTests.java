package com.artconnect.backend.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.mockito.Mockito.mock;
class UserTests {
    @Test
    void testGetAuthorities() {
        // Create a user with a role
        Role role = Role.ADMIN;  // Replace with the appropriate role constant from your implementation
        User user = User.builder()
                .role(role)
                .build();

        // Verify that the user's authorities match the role's authorities
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        Assertions.assertEquals(role.getAuthorities(), authorities);
    }

    @Test
    void testIsAccountNonExpired() {
        User user = new User();
        Assertions.assertTrue(user.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        User user = new User();
        Assertions.assertTrue(user.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        User user = new User();
        Assertions.assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        User user = User.builder()
                .isEnabled(true) // Set isEnabled explicitly
                .build();
        Assertions.assertTrue(user.isEnabled());
    }

    @Test
    void testGetPassword() {
        // Create a User object with a password
        String expectedPassword = "myPassword";
        User user = User.builder()
                .password(expectedPassword)
                .build();

        // Call the getPassword() method and assert the result
        String actualPassword = user.getPassword();
        Assertions.assertEquals(expectedPassword, actualPassword);
    }

    @Test
    void testGetUsername() {
        // Create a User object with an email
        String expectedEmail = "user@example.com";
        User user = User.builder()
                .email(expectedEmail)
                .build();

        // Call the getUsername() method and assert the result
        String actualEmail = user.getUsername();
        Assertions.assertEquals(expectedEmail, actualEmail);
    }

}
