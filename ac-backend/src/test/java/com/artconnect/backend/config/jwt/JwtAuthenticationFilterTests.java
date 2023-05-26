package com.artconnect.backend.config.jwt;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;

import com.artconnect.backend.config.jwt.JwtAuthenticationFilter;
import com.artconnect.backend.config.jwt.JwtService;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


public class JwtAuthenticationFilterTests {

    @Mock
    private JwtService jwtService;

    @Mock
    private ReactiveUserDetailsService userDetailsService;

    @InjectMocks
    private JwtAuthenticationFilter jwtFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void filterWithEmptyTokenShouldCallChainFilter() {
        ServerWebExchange exchange = mock(ServerWebExchange.class);
        WebFilterChain chain = mock(WebFilterChain.class);
        ServerHttpRequest request = mock(ServerHttpRequest.class);

        when(jwtService.extractUsername(anyString())).thenReturn(null);
        when(request.getHeaders()).thenReturn(HttpHeaders.EMPTY);
        when(exchange.getRequest()).thenReturn(request);

        StepVerifier.create(jwtFilter.filter(exchange, chain))
                .equals(null);

        verify(chain, times(1)).filter(exchange);
    }

    @Test
    void filterWithValidTokenShouldAuthenticateUserAndCallChainFilter() {
        // Given
        String token = "validToken";
        String userEmail = "test@example.com";
        UserDetails userDetails = User.withUsername("test@example.com")
                .password("password")
                .authorities("ROLE_USER")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        ServerWebExchange exchange = mock(ServerWebExchange.class);
        WebFilterChain chain = mock(WebFilterChain.class);
        ServerHttpRequest request = mock(ServerHttpRequest.class);

        // When
        when(jwtService.extractUsername(token)).thenReturn(userEmail);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(true);
        when(userDetailsService.findByUsername(userEmail)).thenReturn(Mono.just(userDetails));
        when(request.getHeaders()).thenReturn(headers);
        when(exchange.getRequest()).thenReturn(request);
        when(chain.filter(exchange)).thenReturn(Mono.empty());
        when(chain.filter(exchange).contextCapture()).thenReturn(Mono.empty());

        // Then
        Mono<Void> foundUserMono = jwtFilter.filter(exchange, chain);
        StepVerifier.create(foundUserMono)
                .verifyComplete();
    }

    @Test
    void filterWithInvalidTokenShouldNotAuthenticateUserAndCallChainFilter() {
        // Given
        String token = "invalidToken";
        String userEmail = "test@example.com";
        UserDetails userDetails = User.withUsername("test@example.com")
                .password("password")
                .authorities("ROLE_USER")
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        ServerWebExchange exchange = mock(ServerWebExchange.class);
        WebFilterChain chain = mock(WebFilterChain.class);
        ServerHttpRequest request = mock(ServerHttpRequest.class);

        // When
        when(jwtService.extractUsername(token)).thenReturn(userEmail);
        when(jwtService.isTokenValid(token, userDetails)).thenReturn(false);
        when(userDetailsService.findByUsername(userEmail)).thenReturn(Mono.just(userDetails));
        when(request.getHeaders()).thenReturn(headers);
        when(exchange.getRequest()).thenReturn(request);
        when(chain.filter(exchange)).thenReturn(Mono.empty());

        // Then
        Mono<Void> foundUserMono = jwtFilter.filter(exchange, chain);
        StepVerifier.create(foundUserMono)
                .verifyComplete();
    }

}
