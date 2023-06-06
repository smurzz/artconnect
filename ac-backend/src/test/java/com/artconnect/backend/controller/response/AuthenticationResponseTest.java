package com.artconnect.backend.controller.response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class AuthenticationResponseTest {
    @Test
  public void testGetSetAccessToken() {
    // Arrange
    String expectedAccessToken = "myAccessToken";
    AuthenticationResponse response = new AuthenticationResponse();

    // Act
    response.setAccessToken(expectedAccessToken);
    String actualAccessToken = response.getAccessToken();

    // Assert
    Assertions.assertEquals(expectedAccessToken, actualAccessToken);
  }

  @Test
  public void testGetSetRefreshToken() {
    // Arrange
    String expectedRefreshToken = "myRefreshToken";
    AuthenticationResponse response = new AuthenticationResponse();

    // Act
    response.setRefreshToken(expectedRefreshToken);
    String actualRefreshToken = response.getRefreshToken();

    // Assert
    Assertions.assertEquals(expectedRefreshToken, actualRefreshToken);
  }

    @Test
    public void testNoArgsConstructor() {
        // Arrange & Act
        AuthenticationResponse response = new AuthenticationResponse();

        // Assert
        Assertions.assertNull(response.getAccessToken());
        Assertions.assertNull(response.getRefreshToken());
    }

    @Test
    public void testAllArgsConstructor() {
        // Arrange
        String expectedAccessToken = "myAccessToken";
        String expectedRefreshToken = "myRefreshToken";

        // Act
        AuthenticationResponse response = new AuthenticationResponse(expectedAccessToken, expectedRefreshToken);

        // Assert
        Assertions.assertEquals(expectedAccessToken, response.getAccessToken());
        Assertions.assertEquals(expectedRefreshToken, response.getRefreshToken());
    }

    @Test
    public void testBuilder() {
        // Arrange
        String expectedAccessToken = "myAccessToken";
        String expectedRefreshToken = "myRefreshToken";

        // Act
        AuthenticationResponse response = AuthenticationResponse.builder()
                .accessToken(expectedAccessToken)
                .refreshToken(expectedRefreshToken)
                .build();

        // Assert
        Assertions.assertEquals(expectedAccessToken, response.getAccessToken());
        Assertions.assertEquals(expectedRefreshToken, response.getRefreshToken());
    }

    @Test
    public void testSetAccessToken() {
        // Arrange
        String expectedAccessToken = "myAccessToken";
        AuthenticationResponse response = new AuthenticationResponse();

        // Act
        response.setAccessToken(expectedAccessToken);

        // Assert
        Assertions.assertEquals(expectedAccessToken, response.getAccessToken());
    }

    @Test
    public void testSetRefreshToken() {
        // Arrange
        String expectedRefreshToken = "myRefreshToken";
        AuthenticationResponse response = new AuthenticationResponse();

        // Act
        response.setRefreshToken(expectedRefreshToken);

        // Assert
        Assertions.assertEquals(expectedRefreshToken, response.getRefreshToken());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        AuthenticationResponse response1 = new AuthenticationResponse("token1", "token1");
        AuthenticationResponse response2 = new AuthenticationResponse("token1", "token1");
        AuthenticationResponse response3 = new AuthenticationResponse("token2", "token2");

        // Assert
        Assertions.assertEquals(response1, response2);
        Assertions.assertNotEquals(response1, response3);
        Assertions.assertEquals(response1.hashCode(), response2.hashCode());
        Assertions.assertNotEquals(response1.hashCode(), response3.hashCode());
    }

    @Test
    public void testToString() {
        // Arrange
        String expectedToString = "AuthenticationResponse(accessToken=myAccessToken, refreshToken=myRefreshToken)";
        AuthenticationResponse response = new AuthenticationResponse("myAccessToken", "myRefreshToken");

        // Assert
        Assertions.assertEquals(expectedToString, response.toString());
    }

    @Test
    public void testJsonPropertyAnnotation() throws NoSuchFieldException {
        // Arrange
        AuthenticationResponse response = new AuthenticationResponse();

        // Act
        Field accessTokenField = response.getClass().getDeclaredField("accessToken");
        Field refreshTokenField = response.getClass().getDeclaredField("refreshToken");

        String accessTokenJsonProperty = accessTokenField.getAnnotation(JsonProperty.class).value();
        String refreshTokenJsonProperty = refreshTokenField.getAnnotation(JsonProperty.class).value();

        // Assert
        Assertions.assertEquals("access_token", accessTokenJsonProperty);
        Assertions.assertEquals("refresh_token", refreshTokenJsonProperty);
    }

}
