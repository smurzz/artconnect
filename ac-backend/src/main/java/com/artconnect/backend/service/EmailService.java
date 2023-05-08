package com.artconnect.backend.service;

import java.io.UnsupportedEncodingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service("emailService")
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSender javaMailSender;

    @Async
    public void sendEmail(String to, String subject, String email) {
    	MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        
        try {
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setFrom("service@artconnect.com", "ArtConnect");
			helper.setText(email, true);
	        javaMailSender.send(mimeMessage);
		} catch (MessagingException | UnsupportedEncodingException e) {
			throw new IllegalStateException("failed to send email");
		}
    }
}