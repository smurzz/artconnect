package com.artconnect.backend.controller.response;

import java.util.List;
import java.util.Set;

import com.artconnect.backend.model.gallery.Gallery;
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
	
	private List<ArtWorkResponse> artworks;
	
	private Set<GalleryCategory> categories;
	
	private Double ranking;
	
	public static GalleryResponse fromGallery(Gallery gallery, List<ArtWorkResponse> artworks) {
		GalleryResponse galleryResponse = GalleryResponse.builder()
				.id(gallery.getId())
				.ownerId(gallery.getOwnerId())
				.ownerName(gallery.getOwnerName())
				.title(gallery.getTitle())
				.description(gallery.getDescription())
				.artworks(artworks)
				.categories(gallery.getCategories())
				.ranking(gallery.getRating())
				.build();
        return galleryResponse;
	}
}
