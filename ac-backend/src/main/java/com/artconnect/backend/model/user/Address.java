package com.artconnect.backend.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

	private String street;
	
	private String postalCode;
	
	private String city;
	
	private String country;
}
