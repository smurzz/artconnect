package com.artconnect.backend.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "email-contact-attempts")
public class EmailContactAttempt {
	
	@Id
    private String id;   
	
	private String senderId;
        
    private String senderName;
    
    private String senderEmail;
    
    private String artistId;
    
    private String artistName;
    
    private String artworkLink;
    
    private String message;
    
    private Date sentAt;

}
