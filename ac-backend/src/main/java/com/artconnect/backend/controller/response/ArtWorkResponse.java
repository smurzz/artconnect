package com.artconnect.backend.controller.response;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.artconnect.backend.model.Image;
import com.artconnect.backend.model.artwork.Comment;
import com.artconnect.backend.model.artwork.Dimension;

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
    
    private int yearOfCreation;
    
    private List<String> materials;
    
    private Dimension dimension;
    
    private double price;
    
    private String location;
    
    private Date createdAt;
    
	private List<Comment> comments;
	
	private String ownerId;
	
	private String galleryId;
	
	private String ownerName;
	
	private String galleryTitle;

	private double likes;

}
