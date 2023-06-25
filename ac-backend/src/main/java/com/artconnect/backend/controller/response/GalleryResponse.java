package com.artconnect.backend.controller.response;

import java.util.List;

import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.model.gallery.GalleryCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class GalleryResponse {

	private String id;
	
	private String ownerId;
	
	private String ownerName;
	
	private String title;
	
	private String description;
	
	private List<ArtWork> artworks;
	
	private List<GalleryCategory> categories;
	
	private double ranking;
}
