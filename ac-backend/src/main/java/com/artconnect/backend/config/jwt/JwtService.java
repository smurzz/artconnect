package com.artconnect.backend.config.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${application.security.jwt.secretKey}")
	private String secretKey;
	@Value("${application.security.jwt.validityInMs}")
	private long tokenValidity;
	@Value("${application.security.jwt.refresh-token.validityInMs}")
	private long refreshTokenValidity;
	@Value("${application.security.jwt.confirm-token.validityInMs}")
	private long confirmTokenValidity;

	// extracts the username from the token
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	// extracts a claim from the token
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	// generates a token for the provided UserDetails object with an empty map of extra claims
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	// generates a token for the provided UserDetails object with the provided extra claims
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return createToken(extraClaims, userDetails, tokenValidity);
	}
	
	// generates a refresh token for the provided UserDetails object with an empty map of extra claims
	public String generateRefreshToken(UserDetails userDetails) {
		return createToken(new HashMap<>(), userDetails, refreshTokenValidity);
	}
	
	// generates a confirmation token for the provided UserDetails object with an empty map of extra claims
	public String generateConfirmationToken(UserDetails userDetails) {
		return createToken(new HashMap<>(), userDetails, confirmTokenValidity);
	}

	// creates a token with the provided extra claims, subject, issued at and expiration time
	private String createToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	// validates if the provided token belongs to the provided user details
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	// checks if the provided token is expired
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// extracts the expiration date from the token
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	// parses the provided token and returns all the claims in it
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	// returns a Key object that can be used for signing or verifying the JWT token
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
