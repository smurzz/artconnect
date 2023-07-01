package com.artconnect.backend.controller.request;

import java.util.List;
import java.util.Set;

import com.artconnect.backend.model.artwork.ArtDirection;
import com.artconnect.backend.model.artwork.Dimension;
import com.artconnect.backend.validation.MaxYear;
import com.artconnect.backend.validation.ValidArtDirection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArtWorkRequest {
	
	@NotBlank
	private String title;
    
    private String description;
    
    @MaxYear
    private Integer yearOfCreation;
    
    @Size(max = 10, message = "Materials list can have at most 10 strings")
    private List<String> materials;
    
    private Dimension dimension;
    
    @PositiveOrZero(message = "Price must be a positive or zero value")
    private Double price;
    
    @Size(max = 10, message = "Tags list can have at most 10 strings")
    private List<String> tags;
    
    @Size(max = 10, message = "Max size is 10")
	@ValidArtDirection
    private Set<ArtDirection> artDirections;
    
    private String location;
    
}
