package com.artconnect.backend.model.user;

import com.artconnect.backend.validation.MaxYear;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exhibition {
	
	private String title;
	
	private String location;
	
	private Integer year;
	
	private String description;
}