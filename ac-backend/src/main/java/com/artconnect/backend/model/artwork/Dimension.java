package com.artconnect.backend.model.artwork;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dimension {

	private double height;
	
	private double width; 
	
	private double depth;
}
