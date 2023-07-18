package com.artconnect.backend.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contacts {

	private Long telefonNumber;
	
    private Address address;
    
    private String website;
}