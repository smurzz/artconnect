package com.artconnect.backend.model.user;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.artconnect.backend.model.Image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User implements UserDetails {
	
	@Id
	private String id;
	
	private String firstname;

	private String lastname;
	
    @Indexed(unique = true)
	private String email;
	
	private String password;
	
	private LocalDate dateOfBirthday;
	
    private Status isDateOfBirthVisible;
	
	private Date createdAt;
	
	private Status isAccountEnabled;
	
	private Role role;
	
	private Image profilePhoto;
	
	private String biography;
	
	private List<Exhibition> exhibitions;
	
	private Contacts contacts;
	
	private List<SocialMedia> socialMedias;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
