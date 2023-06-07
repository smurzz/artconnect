package com.artconnect.backend.service;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.controller.request.AuthenticationRequest;
import com.artconnect.backend.controller.request.RegisterRequest;
import com.artconnect.backend.controller.response.AuthenticationResponse;
import com.artconnect.backend.model.Role;
import com.artconnect.backend.model.User;
import com.artconnect.backend.repository.UserRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final ReactiveAuthenticationManager authenticationManager;
	private final ReactiveUserDetailsService userDetailsService;
	private final EmailService emailService;
	
	public Mono<String> register(RegisterRequest request) {
		User user = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.createdAt(new Date())
				.isEnabled(false)
				.role(Role.USER)
				.build();
		return userRepository.save(user)
				.flatMap(savedUser -> {
					String confirmToken = jwtService.generateConfirmationToken(user);
					String link = "http://localhost:8080/auth/confirm-account?token=" + confirmToken;
					String messageBodyString = buildEmail(savedUser.getFirstname(), link);
					String subject = "Complete Registration by ArtConnect!";
					emailService.sendEmail(user.getEmail(), subject, messageBodyString);

					return Mono.just("Verify email by the link sent on your email address");
				})
				.onErrorResume(IllegalStateException.class, 
					    err -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send email")));

	}
	
	public Mono<String> confirmEmail(String confirmToken) {
		if(!confirmToken.isEmpty()) {
			try {
				String userEmail = jwtService.extractUsername(confirmToken);
				return userDetailsService.findByUsername(userEmail)
						.flatMap(userDetails -> {
							if(jwtService.isTokenValid(confirmToken, userDetails)) {
								return userRepository.findByEmail(userEmail)
				                        .flatMap(user -> {
				                            user.setEnabled(true);
				                            return userRepository.save(user)
				                            		.thenReturn(succeedConfirmEmail());
				                        });	
							} else {
								return Mono.just(failedConfirmEmail());
							}
						});	
			} catch (ExpiredJwtException | SignatureException | MalformedJwtException | IllegalArgumentException e) {
				return Mono.just(failedConfirmEmail());
			}
		}
		return Mono.just(failedConfirmEmail());

	}

	public Mono<AuthenticationResponse> login(AuthenticationRequest request) {
		return authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()))
				.then(userRepository.findByEmail(request.getEmail()))
				.flatMap(user -> {
					if(user.isEnabled()) {
						String jwtToken = jwtService.generateToken(user);
						String refreshToken = jwtService.generateRefreshToken(user);
						return Mono.just(AuthenticationResponse.builder()
								.accessToken(jwtToken)
								.refreshToken(refreshToken)
								.build());
					} else {
						return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Your account has not been confirmed. Please check your email to confirm your registration."));
					}
				})
				.onErrorResume(BadCredentialsException.class,
						err -> Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password")));
	}

	public Mono<AuthenticationResponse> refreshToken(ServerHttpRequest request, ServerHttpResponse response) {
		final String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userEmail;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST));
		}
		refreshToken = authHeader.substring(7);
		userEmail = jwtService.extractUsername(refreshToken);
		if (userEmail != null) {
			return userRepository.findByEmail(userEmail)
					.flatMap(user -> {
						if (jwtService.isTokenValid(refreshToken, user)) {
							String accessToken = jwtService.generateToken(user);
							AuthenticationResponse authResponse = AuthenticationResponse.builder()
									.accessToken(accessToken)
									.refreshToken(refreshToken)
									.build();
							response.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
							return Mono.just(authResponse);
						} else {
							return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED));
						}
			});
		}
		return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED));
	}

	private String buildEmail(String name, String link) {
		return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
	}
	
	private String succeedConfirmEmail() {
		return "<html lang=\"en\">\r\n"
				+ "<head>\r\n"
				+ "	<meta charset=\"utf-8\" />\r\n"
				+ "	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\" />\r\n"
				+ "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
				+ "	<title></title>\r\n"
				+ "	<link href='https://fonts.googleapis.com/css?family=Lato:300,400|Montserrat:700' rel='stylesheet' type='text/css'>\r\n"
				+ "	<style>\r\n"
				+ "		@import url(//cdnjs.cloudflare.com/ajax/libs/normalize/3.0.1/normalize.min.css);\r\n"
				+ "		@import url(//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css);\r\n"
				+ "	</style>\r\n"
				+ "	<link rel=\"stylesheet\" href=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/default_thank_you.css\">\r\n"
				+ "	<script src=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/jquery-1.9.1.min.js\"></script>\r\n"
				+ "	<script src=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/html5shiv.js\"></script>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "	<header class=\"site-header\" id=\"header\">\r\n"
				+ "		<h1 class=\"site-header__title\" data-lead-id=\"site-header-title\">THANK YOU!</h1>\r\n"
				+ "	</header>\r\n"
				+ "\r\n"
				+ "	<div class=\"main-content\">\r\n"
				+ "		<i class=\"fa fa-check main-content__checkmark\" id=\"checkmark\"></i>\r\n"
				+ "		<p class=\"main-content__body\" data-lead-id=\"main-content-body\">Congratulations! Your registration on ArtConnect platform has been confirmed successfully. You can now log in to your account and start exploring our community of artists and art lovers. Thank you for joining us and we wish you an exciting and creative experience!</p>\r\n"
				+ "	</div>\r\n"
				+ "\r\n"
				+ "	<footer class=\"site-footer\" id=\"footer\">\r\n"
				+ "		<p class=\"site-footer__fineprint\" id=\"fineprint\">Copyright ©2014 | All Rights Reserved</p>\r\n"
				+ "	</footer>\r\n"
				+ "</body>\r\n"
				+ "</html>";
	}

	private String failedConfirmEmail() {
		return "<html lang=\"en\">\r\n"
				+ "<head>\r\n"
				+ "	<meta charset=\"utf-8\" />\r\n"
				+ "	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\" />\r\n"
				+ "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
				+ "	<title></title>\r\n"
				+ "	<link href='https://fonts.googleapis.com/css?family=Lato:300,400|Montserrat:700' rel='stylesheet' type='text/css'>\r\n"
				+ "	<style>\r\n"
				+ "		@import url(//cdnjs.cloudflare.com/ajax/libs/normalize/3.0.1/normalize.min.css);\r\n"
				+ "		@import url(//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css);\r\n"
				+ "	</style>\r\n"
				+ "	<link rel=\"stylesheet\" href=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/default_thank_you.css\">\r\n"
				+ "	<script src=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/jquery-1.9.1.min.js\"></script>\r\n"
				+ "	<script src=\"https://2-22-4-dot-lead-pages.appspot.com/static/lp918/min/html5shiv.js\"></script>\r\n"
				+ "</head>\r\n"
				+ "<body>\r\n"
				+ "	<header class=\"site-header\" id=\"header\">\r\n"
				+ "		<h1 class=\"site-header__title\" data-lead-id=\"site-header-title\">Sorry...</h1>\r\n"
				+ "	</header>\r\n"
				+ "\r\n"
				+ "	<div class=\"main-content\">\r\n"
				+ "		<i class=\"fa fa-times main-content__checkmark\" id=\"checkmark\"></i>\r\n"
				+ "		<p class=\"main-content__body\" data-lead-id=\"main-content-body\">We're sorry, but we couldn't confirm your registration on ArtConnect platform because the confirmation link has expired or is not valid anymore. Please make sure to use the latest link that we sent you in the confirmation email.</p>\r\n"
				+ "	</div>\r\n"
				+ "\r\n"
				+ "	<footer class=\"site-footer\" id=\"footer\">\r\n"
				+ "		<p class=\"site-footer__fineprint\" id=\"fineprint\">Copyright ©2014 | All Rights Reserved</p>\r\n"
				+ "	</footer>\r\n"
				+ "</body>\r\n"
				+ "</html>";
	}
}