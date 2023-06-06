package com.artconnect.backend.config.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import com.artconnect.backend.model.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

public class JwtServiceTests {

	@Mock
	private JwtService jwtService;

	@Mock
	private UserDetails userDetails;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testExtractUsernameTrue() {
		String token = "valid-token";
		String username = "john@example.com";

		when(jwtService.extractUsername(token)).thenReturn(username);
		String name = jwtService.extractUsername(token);

		assertEquals(name, username);
	}
	
	@Test
	void testExtractUsernameFalse() {
		String token = "invalid-token";

		when(jwtService.extractUsername(token)).thenReturn(null);
		String name = jwtService.extractUsername(token);

		assertNull(name);
	}
	
	@Test
    void testExtractClaimReturnEmail() {
		String token = "valid-token";
		String expectedEmail = "test@example@gmail.com";
		Function<Claims, String> claimsResolver = Claims::getSubject;
		
		when(jwtService.extractClaim(token, claimsResolver)).thenReturn(expectedEmail);
		
		String email = jwtService.extractClaim(token, claimsResolver);

		assertEquals(expectedEmail, email);
    }
	
	@Test
    void testExtractClaimReturnNull() {
		String token = "valid-token";
		Function<Claims, String> claimsResolver = Claims::getSubject;
		
		when(jwtService.extractClaim(token, claimsResolver)).thenReturn(null);
		
		String email = jwtService.extractClaim(token, claimsResolver);
		assertNull(email);
    }
	
	@Test
	void testGenerateTokenByUserDetailsReturnToken() {
		UserDetails userDetails = User.builder()
				.username("test@example.com")
				.password("1234")
				.roles(Role.USER.name())
				.build();
				
		when(jwtService.generateToken(new HashMap<>(), userDetails)).thenReturn("token");
		when(jwtService.generateToken(userDetails)).thenReturn("token");
		
		String hashMapValidToken = jwtService.generateToken(new HashMap<>(), userDetails);
		String validToken = jwtService.generateToken(userDetails);
		
		assertEquals(hashMapValidToken, "token");
		assertEquals(validToken, "token");
	}

	@Test
	void testGenerateTokenByUserDetailsReturnNull() {
		UserDetails userDetails = null;
				
		when(jwtService.generateToken(new HashMap<>(), userDetails)).thenReturn(null);
		when(jwtService.generateToken(userDetails)).thenReturn(null);
		
		String hashMapValidToken = jwtService.generateToken(new HashMap<>(), userDetails);
		String validToken = jwtService.generateToken(userDetails);
		
		assertNull(hashMapValidToken);
		assertNull(validToken);
	}
	
	@Test
	void testGenerateRefreshTokenByUserDetailsReturnToken() {
		UserDetails userDetails = User.builder()
				.username("test@example.com")
				.password("1234")
				.roles(Role.USER.name())
				.build();
				
		when(jwtService.generateRefreshToken(userDetails)).thenReturn("refresh-token");
		
		String refreshToken = jwtService.generateRefreshToken(userDetails);
		assertEquals(refreshToken, "refresh-token");
	}
	
	@Test
	void testGenerateRefreshTokenByUserDetailsReturnNull() {
		UserDetails userDetails = null;
				
		when(jwtService.generateRefreshToken(userDetails)).thenReturn(null);
		
		String refreshToken = jwtService.generateRefreshToken(userDetails);
		assertNull(refreshToken);
	}
	
	@Test
	void testGenerateConfirmTokenByUserDetailsReturnToken() {
		UserDetails userDetails = User.builder()
				.username("test@example.com")
				.password("1234")
				.roles(Role.USER.name())
				.build();
				
		when(jwtService.generateConfirmationToken(userDetails)).thenReturn("confirm-token");
		
		String confirmToken = jwtService.generateConfirmationToken(userDetails);
		assertEquals(confirmToken, "confirm-token");
	}
	
	@Test
	void testGenerateConfirmTokenByUserDetailsReturnNull() {
		UserDetails userDetails = null;
				
		when(jwtService.generateConfirmationToken(userDetails)).thenReturn(null);

		String confirmToken = jwtService.generateConfirmationToken(userDetails);
		assertNull(confirmToken);
	}

	@Test
	void isTokenValidValidTokenAndUserDetailsReturnsTrue() {
		String token = "exampleToken";
		UserDetails userDetails = User.builder()
				.username("test@example.com")
				.password("1234")
				.roles(Role.USER.name())
				.build();

		when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);

		boolean isTokenValid = jwtService.isTokenValid(token, userDetails);
		assertTrue(isTokenValid);
	}
	
	@Test
	void isTokenValidInvalidTokenAndUserDetailsReturnsNull() {
		String invaliToken = "invalid-token";
		UserDetails userDetails = User.builder()
				.username("test@example.com")
				.password("1234")
				.roles(Role.USER.name())
				.build();

		when(jwtService.isTokenValid(invaliToken, userDetails)).thenReturn(false);

		boolean isTokenInvalid = jwtService.isTokenValid(invaliToken, userDetails);
		assertFalse(isTokenInvalid);
	}
}
