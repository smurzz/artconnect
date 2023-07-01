package com.artconnect.backend.controller.response;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.artconnect.backend.model.Image;
import com.artconnect.backend.model.artwork.ArtDirection;
import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.model.artwork.Comment;
import com.artconnect.backend.model.artwork.Dimension;
import com.artconnect.backend.model.user.Status;
import com.artconnect.backend.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class ArtWorkResponse {
	
	private String id;
	
	private String title;
	
	private List<Image> images;
    
    private String description;
    
    private Integer yearOfCreation;
    
    private List<String> materials;
    
    private List<String> tags;
    
    private Set<ArtDirection> artDirections;
    
    private Dimension dimension;
    
    private Double price;
    
    private String location;
    
    private Date createdAt;
    
	private List<Comment> comments;
	
	private String ownerId;
	
	private String galleryId;
	
	private String ownerName;
	
	private String galleryTitle;

	private Integer likes;
	
	
	public static ArtWorkResponse fromArtWork(ArtWork artwork, List<Image> images) {
		ArtWorkResponse artworkResponse = ArtWorkResponse.builder()
				.id(artwork.getId())
				.title(artwork.getTitle())
				.images(images)
				.description(artwork.getDescription())
				.yearOfCreation(artwork.getYearOfCreation())
				.materials(artwork.getMaterials())
				.tags(artwork.getTags())
				.artDirections(artwork.getArtDirections())
				.dimension(artwork.getDimension())
				.price(artwork.getPrice())
				.location(artwork.getLocation())
				.createdAt(artwork.getCreatedAt())
				.comments(artwork.getComments())
				.ownerId(artwork.getOwnerId())
				.galleryId(artwork.getGalleryId())
				.ownerName(artwork.getOwnerName())
				.galleryTitle(artwork.getGalleryTitle())
				.likes(artwork.getLikes())
				.build();
        return artworkResponse;
	}

}
