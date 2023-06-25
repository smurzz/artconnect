package com.artconnect.backend.controller.request;

import java.time.LocalDate;
import java.util.List;

import com.artconnect.backend.model.Image;
import com.artconnect.backend.model.user.Contacts;
import com.artconnect.backend.model.user.Exhibition;
import com.artconnect.backend.model.user.Role;
import com.artconnect.backend.model.user.SocialMedia;
import com.artconnect.backend.model.user.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
	
	private String id;
	
	private String firstname;

	private String lastname;
	
	private String email;
	
	private String password;
	
	private LocalDate dateOfBirthday;
	
	private Status isDateOfBirthVisible;
	
	private Status isAccountEnabled;
	
	private Role role;
	
	private Image profilePhoto;
	
	private String biography;
	
	private List<Exhibition> exhibitions;
	
	private Contacts contacts;
	
	private List<SocialMedia> socialMedias;

}
