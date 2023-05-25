package com.artconnect.backend.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	
	 	@NotBlank
	    @Size(min = 2, max = 30)
	    private String firstname;

	    @NotBlank
	    @Size(min = 2, max = 30)
	    private String lastname;

	    @NotBlank
	    @Size(max = 40)
	    @Email
	    private String email;

	    @NotBlank
	    @Size(min = 3, max = 20)
	    private String password;
}
