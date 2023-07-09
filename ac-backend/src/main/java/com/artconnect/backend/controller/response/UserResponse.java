package com.artconnect.backend.controller.response;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.artconnect.backend.model.Image;
import com.artconnect.backend.model.user.Contacts;
import com.artconnect.backend.model.user.Exhibition;
import com.artconnect.backend.model.user.SocialMedia;
import com.artconnect.backend.model.user.Status;
import com.artconnect.backend.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
	
	private String id;
	
	private String firstname;

	private String lastname;
	
	private LocalDate dateOfBirthday;
	
	private Date createdAt;
	
	private Image profilePhoto;
	
	private String biography;
	
	private List<Exhibition> exhibitions;
	
	private Contacts contacts;
	
	private List<SocialMedia> socialMedias;
	
	public static UserResponse fromUser(User user) {
		UserResponse userResponse = UserResponse.builder()
				.id(user.getId())
				.firstname(user.getFirstname())
				.lastname(user.getLastname())
				.createdAt(user.getCreatedAt())
				.profilePhoto(user.getProfilePhoto())
				.biography(user.getBiography())
				.exhibitions(user.getExhibitions())
				.contacts(user.getContacts())
				.socialMedias(user.getSocialMedias())
				.build();  
		if(user.getIsDateOfBirthVisible() != null && user.getIsDateOfBirthVisible().equals(Status.PUBLIC)) {
			userResponse.setDateOfBirthday(user.getDateOfBirthday());
		}
        return userResponse;
	}

}
