package com.artconnect.backend.controller.request;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.artconnect.backend.model.gallery.GalleryCategory;
import com.artconnect.backend.validation.ValidGalleryCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GalleryRequest {
	
	private String id;

	private String ownerId;

	private String ownerName;
	
	@NotBlank
	private String title;
	
	private String description;
	
	@Size(max = 4, message = "Max size is 4")
	@ValidGalleryCategory
	private Set<GalleryCategory> categories;

	private List<String> artworkIds;

	private Map<String, Integer> evaluations;
	
}
