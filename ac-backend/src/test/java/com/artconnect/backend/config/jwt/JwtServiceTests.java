package com.artconnect.backend.config.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Key;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import com.artconnect.backend.model.user.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtServiceTests {
	
	private final String secretKey = "4E635266556A586E327234753778214125442A472D4B6150645367566B597033";
	
	private final long tokenValidity = 60000;
	
	private final long refreshTokenValidity = 900000;
	
	private final long confirmTokenValidity = 10000;
	
	@InjectMocks
	private JwtService jwtService;
	
	@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
	    ReflectionTestUtils.setField(jwtService, "secretKey", secretKey);
	    ReflectionTestUtils.setField(jwtService, "tokenValidity", tokenValidity);
	    ReflectionTestUtils.setField(jwtService, "refreshTokenValidity", refreshTokenValidity);
	    ReflectionTestUtils.setField(jwtService, "confirmTokenValidity", confirmTokenValidity);
    }
	
	@Test
	void testExtractUsernameWithValidToken() {
		String expectedEmail = "test@example.com";
		UserDetails userDetails = User.builder()
				.username("test@example.com")
				.password("1234")
				.roles(Role.USER.name())
				.build();
	   
	 	String token = jwtService.generateToken(userDetails);
		String name = jwtService.extractUsername(token);

		assertEquals(name, expectedEmail);
	}
	
	@Test
	void testExtractUsernameWithInvalidToken() {
	    String token = "invalid-token";

		assertThrows(MalformedJwtException.class, () -> {
	        jwtService.extractUsername(token);
	    });
	}
	
	@Test
    void testExtractClaimReturnEmail() {
		String expectedEmail = "test@example.com";
		UserDetails userDetails = User.builder()
				.username("test@example.com")
				.password("1234")
				.roles(Role.USER.name())
				.build();
	   
	 	String token = jwtService.generateToken(userDetails);
	    String email = jwtService.extractClaim(token, Claims::getSubject);

	    assertEquals(expectedEmail, email);
    }
	
	@Test
    void testExtractClaimReturnError() {
	    String token = "invalid-token";

	    assertThrows(MalformedJwtException.class, () -> {
	        jwtService.extractClaim(token, Claims::getSubject);
	    });
    }
	
	@Test
	void testGenerateTokenByUserDetailsReturnToken() {
		UserDetails userDetails = User.builder()
				.username("test@example.com")
				.password("1234")
				.roles(Role.USER.name())
				.build();
				
		String hashMapValidToken = jwtService.generateToken(new HashMap<>(), userDetails);
		String validToken = jwtService.generateToken(userDetails);
		
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		Key signInKeyClaims = Keys.hmacShaKeyFor(keyBytes);
		
	    Claims claims = Jwts.parserBuilder()
	            .setSigningKey(signInKeyClaims)
	            .build()
	            .parseClaimsJws(validToken)
	            .getBody();
		
		assertNotNull(hashMapValidToken);
		assertNotNull(validToken);
		assertEquals("test@example.com", claims.getSubject());
	    assertTrue(hashMapValidToken.matches("[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]+")); 
	}	
	
	@Test
	void testGenerateTokenByUserDetailsReturnError() {		
		assertThrows(NullPointerException.class, () -> {
			jwtService.generateToken(new HashMap<>(), null);
	    });
	}
	
	@Test 
	void testGenerateRefreshTokenByUserDetailsReturnToken() {
		UserDetails userDetails = User.builder()
				.username("test@example.com")
				.password("1234")
				.roles(Role.USER.name())
				.build();

		String refreshToken = jwtService.generateRefreshToken(userDetails);
		assertNotNull(refreshToken);
	    assertTrue(refreshToken.matches("[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]+")); 
	}
	
	@Test
	void testGenerateRefreshTokenByUserDetailsReturnNull() {
		assertThrows(NullPointerException.class, () -> {
			jwtService.generateRefreshToken(null);
	    });
	}
	
	@Test 
	void testGenerateConfirmTokenByUserDetailsReturnToken() {
		UserDetails userDetails = User.builder()
				.username("test@example.com")
				.password("1234")
				.roles(Role.USER.name())
				.build();

		String confirmToken = jwtService.generateConfirmationToken(userDetails);
		assertNotNull(confirmToken);
	    assertTrue(confirmToken.matches("[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_.+/=]+")); 
	}
	
	@Test
	void testGenerateConfirmTokenByUserDetailsReturnNull() {
		assertThrows(NullPointerException.class, () -> {
			jwtService.generateConfirmationToken(null);
	    });
	}
	
	@Test
	void isTokenValidValidTokenAndUserDetailsReturnsTrue() {
		UserDetails userDetails = User.builder()
				.username("test@example.com")
				.password("1234")
				.roles(Role.USER.name())
				.build();

		String token = jwtService.generateToken(userDetails);
		
		boolean isTokenValid = jwtService.isTokenValid(token, userDetails);
		assertTrue(isTokenValid);
	}
}
