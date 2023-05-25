package com.artconnect.backend.config.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {
	
	private final JwtService jwtService;
	private final ReactiveUserDetailsService userDetailsService;

	// Filter method that extracts the JWT token from the request header,
	// validates it and sets the authentication in the security context.
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		final String token = resolveToken(exchange.getRequest());		
		if (!StringUtils.hasText(token)) {
	        return chain.filter(exchange);
	    }
		
		final String userEmail = jwtService.extractUsername(token);
	    if (userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null) {
	        return chain.filter(exchange);
	    }
		
	    return this.userDetailsService.findByUsername(userEmail)
				.flatMap(userDetails -> {
					if (jwtService.isTokenValid(token, userDetails)) {
						UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
		                        userDetails,
		                        null,
		                        userDetails.getAuthorities()
		                );
						return chain.filter(exchange)
			                    .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authToken));
					}
					return chain.filter(exchange);
				});
	}
	
	// Resolves the JWT token from the request header.
	private String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
