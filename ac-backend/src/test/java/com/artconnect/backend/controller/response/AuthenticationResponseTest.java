package com.artconnect.backend.controller.response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.artconnect.backend.controller.response.AuthenticationResponse;
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

}
