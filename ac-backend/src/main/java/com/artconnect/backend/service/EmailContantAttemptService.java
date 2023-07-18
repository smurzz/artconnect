package com.artconnect.backend.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.artconnect.backend.controller.request.EmailContactAttemptRequest;
import com.artconnect.backend.model.EmailContactAttempt;
import com.artconnect.backend.repository.EmailContactAttemptRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EmailContantAttemptService {

	@Value("${frontend.base-url}")
	private String frontendBaseUrl;

	private final EmailContactAttemptRepository emailContactRepository;

	private final UserService userService;

	private final ArtWorkService artWorkService;

	private final EmailService emailService;

	public Mono<String> createByArtwork(String artworkId, EmailContactAttemptRequest emailContactRequest) {
		return userService.getCurrentUser().flatMap(user -> {
			return artWorkService.findById(artworkId).flatMap(artwork -> {
				return userService.findById(artwork.getOwnerId()).flatMap(owner -> {
					EmailContactAttempt emailContactAttempt = EmailContactAttempt.builder().senderId(user.getId())
							.senderName(user.getFirstname() + " " + user.getLastname())
							.senderEmail(user.getEmail())
							.artistId(artwork.getOwnerId())
							.artistName(artwork.getOwnerName())
							.artworkLink(frontendBaseUrl + "/galerie/DetailImage/" + artworkId)
							.message(emailContactRequest.getMessage())
							.sentAt(new Date())
							.build();
					String messageBodyString = buildEmail(emailContactAttempt);
					String subject = "Contact Request by ArtConnect!";
					emailService.sendEmail(owner.getEmail(), subject, messageBodyString);

					return emailContactRepository.save(emailContactAttempt)
							.map(result -> "The contact request was successfully sent to the artist!");
				});
			});
		});
	}
	

	public Mono<String> createByUser(String id, EmailContactAttemptRequest emailContactRequest) {
		return userService.getCurrentUser().flatMap(user -> {
				return userService.findById(id)
						.flatMap(foundUser -> {
							EmailContactAttempt emailContactAttempt = EmailContactAttempt.builder().senderId(user.getId())
									.senderName(user.getFirstname() + " " + user.getLastname())
									.senderEmail(user.getEmail())
									.artistId(id)
									.artistName(foundUser.getFirstname())
									.artworkLink("")
									.message(emailContactRequest.getMessage())
									.sentAt(new Date())
									.build();
							String messageBodyString = buildEmail(emailContactAttempt);
							String subject = "Contact Request by ArtConnect!";
							emailService.sendEmail(foundUser.getEmail(), subject, messageBodyString);

							return emailContactRepository.save(emailContactAttempt)
									.map(result -> "The contact request was successfully sent to the artist!");
						});
		});
	}

	private String buildEmail(EmailContactAttempt emailContactAttempt) {
		return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" + "\n"
				+ "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" + "\n"
				+ "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n"
				+ "    <tbody><tr>\n" + "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" + "        \n"
				+ "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n"
				+ "          <tbody><tr>\n" + "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n"
				+ "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
				+ "                  <tbody><tr>\n" + "                    <td style=\"padding-left:10px\">\n"
				+ "                  \n" + "                    </td>\n"
				+ "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n"
				+ "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Contact Request from "
				+ emailContactAttempt.getSenderName() + "</span>\n" + "                    </td>\n"
				+ "                  </tr>\n" + "                </tbody></table>\n" + "              </a>\n"
				+ "            </td>\n" + "          </tr>\n" + "        </tbody></table>\n" + "        \n"
				+ "      </td>\n" + "    </tr>\n" + "  </tbody></table>\n"
				+ "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
				+ "    <tbody><tr>\n" + "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n"
				+ "      <td>\n" + "        \n"
				+ "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n"
				+ "                  <tbody><tr>\n"
				+ "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n"
				+ "                  </tr>\n" + "                </tbody></table>\n" + "        \n" + "      </td>\n"
				+ "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" + "    </tr>\n"
				+ "  </tbody></table>\n" + "\n" + "\n" + "\n"
				+ "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n"
				+ "    <tbody><tr>\n" + "      <td height=\"30\"><br></td>\n" + "    </tr>\n" + "    <tr>\n"
				+ "      <td width=\"10\" valign=\"middle\"><br></td>\n"
				+ "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n"
				+ "        \n"
				+ "          <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + "Dear  "
				+ emailContactAttempt.getArtistName() + "," + "</p>"
				+ "<p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">"
				+ "You have received a contact request from a user who is interested in your artwork. Below are the details of the contact request: "
				+ "</p>" + "<p style=\"Margin:0 0 10px 0;font-size:19px;line-height:20px\">" + "Sender's Name: "
				+ emailContactAttempt.getSenderName() + "</p>"
				+ "<p style=\"Margin:0 0 10px 0;font-size:19px;line-height:20px\">" + "Sender's Email: "
				+ "<a href=\"mailto:" + emailContactAttempt.getSenderEmail() + "\">Email Sender</a>" + "</p>"
				+ "<p style=\"Margin:0 0 10px 0;font-size:19px;line-height:20px\"> " + "Artwork: " + "<a href=\""
				+ emailContactAttempt.getArtworkLink() + "\">Link to the Artwork</a> " + "</p>"
				+ "<p style=\"Margin:0 0 10px 0;font-size:19px;line-height:20px\">" + "Message: " + "</p>"
				+ "<blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\">"
				+ "<p style=\"Margin:0 0 10px 5px;font-size:16px;line-height:20px;color:#0b0c0c\">"
				+ emailContactAttempt.getMessage() + "</p>" + "</blockquote>"
				+ "\n Please note that the user has expressed their interest in connecting with you. If you are interested in continuing the conversation, we encourage you to respond directly to the sender using the provided email address. "
				+ "<p>Thank you for being a part of our art community!</p>" + "        \n" + "<p>Best regards,</p>"
				+ "\n" + "<p>ArtConnect Team</p>" + "\n" + "      </td>\n"
				+ "      <td width=\"10\" valign=\"middle\"><br></td>\n" + "    </tr>\n" + "    <tr>\n"
				+ "      <td height=\"30\"><br></td>\n" + "    </tr>\n"
				+ "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" + "\n" + "</div></div>";
	}

}
