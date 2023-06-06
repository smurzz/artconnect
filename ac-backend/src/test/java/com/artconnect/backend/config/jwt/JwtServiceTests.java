package com.artconnect.backend.config.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Key;
import java.util.HashMap;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import com.artconnect.backend.model.Role;
import com.artconnect.backend.model.User;
import com.artconnect.backend.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

public class JwtServiceTests {
	
	private final String secretKey = "4E635266556A586E327234753778214125442A472D4B6150645367566B597033";
	
	private final long tokenValidity = 60000;
	
	@InjectMocks
	private JwtService jwtService;

	@Mock
	private UserDetails userDetails;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private Function<Claims, String> claimsResolver;
	
	@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
	    ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);
	    ReflectionTestUtils.setField(jwtService, "tokenValidity", tokenValidity);
    }

	/*
	@Test @Disabled
	void testExtractUsernameTrue() {
		String token = "valid-token";
		String username = "john@example.com";

		when(jwtService.extractUsername(token)).thenReturn(username);
		String name = jwtService.extractUsername(token);

		assertEquals(name, username);
	}
	
	@Test @Disabled
	void testExtractUsernameFalse() {
		String token = "invalid-token";

		when(jwtService.extractUsername(token)).thenReturn(null);
		String name = jwtService.extractUsername(token);

		assertNull(name);
	}
	*/
	
	@Test
    void testExtractClaimReturnEmail() throws Exception {
		String expectedEmail = "test@example.com";
	    Claims mockClaims = mock(Claims.class);
		UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
				.username("test@example.com")
				.password("1234")
				.roles(Role.USER.name())
				.build();
	   
	 	String tokenString = jwtService.generateToken(userDetails);
	 		
	    when(claimsResolver.apply(mockClaims)).thenReturn(expectedEmail);
	    
	    String email = jwtService.extractClaim(tokenString, Claims::getSubject);

	    assertEquals(expectedEmail, email);
    }
	/*
	@Test @Disabled
    void testExtractClaimReturnNull() {
		String token = "valid-token";
		Function<Claims, String> claimsResolver = Claims::getSubject;
		
		when(jwtService.extractClaim(token, claimsResolver)).thenReturn(null);
		
		String email = jwtService.extractClaim(token, claimsResolver);
		assertNull(email);
    }
	
	@Test @Disabled
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

	@Test @Disabled
	void testGenerateTokenByUserDetailsReturnNull() {
		UserDetails userDetails = null;
				
		when(jwtService.generateToken(new HashMap<>(), userDetails)).thenReturn(null);
		when(jwtService.generateToken(userDetails)).thenReturn(null);
		
		String hashMapValidToken = jwtService.generateToken(new HashMap<>(), userDetails);
		String validToken = jwtService.generateToken(userDetails);
		
		assertNull(hashMapValidToken);
		assertNull(validToken);
	}
	
	@Test @Disabled
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
	
	@Test @Disabled
	void testGenerateRefreshTokenByUserDetailsReturnNull() {
		UserDetails userDetails = null;
				
		when(jwtService.generateRefreshToken(userDetails)).thenReturn(null);
		
		String refreshToken = jwtService.generateRefreshToken(userDetails);
		assertNull(refreshToken);
	}
	
	@Test @Disabled
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
	
	@Test @Disabled
	void testGenerateConfirmTokenByUserDetailsReturnNull() {
		UserDetails userDetails = null;
				
		when(jwtService.generateConfirmationToken(userDetails)).thenReturn(null);

		String confirmToken = jwtService.generateConfirmationToken(userDetails);
		assertNull(confirmToken);
	}

	@Test @Disabled
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
	
	@Test @Disabled
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
	*/
}
