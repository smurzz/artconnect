package com.artconnect.backend.controller.request;

import java.util.List;
import java.util.Set;

import com.artconnect.backend.model.artwork.ArtDirection;
import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.model.artwork.Comment;
import com.artconnect.backend.model.artwork.Dimension;
import com.artconnect.backend.validation.MaxYear;
import com.artconnect.backend.validation.NotBlankIfSpecified;
import com.artconnect.backend.validation.ValidArtDirection;

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
public class ArtWorkUpdateRequest {
	
	private String id;
	
	private String ownerId;
	
	private String galleryId;
	
	private String ownerName;
	
	private String galleryTitle;

	@NotBlankIfSpecified
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
        
    private List<String> imagesIds;
    
    private Set<String> likedByUsers;
    
	private List<Comment> comments;
    
	public static ArtWork toArtWork(ArtWorkUpdateRequest artworkRequest) {
		ArtWork artwork = ArtWork.builder()
				.id(artworkRequest.getId())
				.title(artworkRequest.getTitle())
				.imagesIds(artworkRequest.getImagesIds())
				.description(artworkRequest.getDescription())
				.yearOfCreation(artworkRequest.getYearOfCreation())
				.materials(artworkRequest.getMaterials())
				.tags(artworkRequest.getTags())
				.artDirections(artworkRequest.getArtDirections())
				.dimension(artworkRequest.getDimension())
				.price(artworkRequest.getPrice())
				.location(artworkRequest.getLocation())
				.comments(artworkRequest.getComments())
				.ownerId(artworkRequest.getOwnerId())
				.galleryId(artworkRequest.getGalleryId())
				.ownerName(artworkRequest.getOwnerName())
				.galleryTitle(artworkRequest.getGalleryTitle())
				.likedByUsers(artworkRequest.getLikedByUsers())
				.build();
        return artwork;
	}
}
