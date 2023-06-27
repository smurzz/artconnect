package com.artconnect.backend.model.artwork;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "artwork")
public class ArtWork {
	
	public final static int MAX_NUM_IMAGES = 5;
	
	@Id
	private String id;
	
	private String ownerId;
	
	private String galleryId;
	
	private String ownerName;
	
	private String galleryTitle;
	
	private String title;
	
	private List<String> imagesIds;
    
    private String description;
    
    private Integer yearOfCreation;
    
    private Set<String> likedByUsers;
    
    private List<String> materials;
    
    private Dimension dimension;
    
    private Double price;
    
    private List<String> tags;
    
    private String location;
    
    private Date createdAt;
    
	private List<Comment> comments;

	public void setLike(String userEmail) {
		if (likedByUsers == null) {
			likedByUsers = new HashSet<>();
		}

		if (likedByUsers.contains(userEmail)) {
			likedByUsers.remove(userEmail);
		} else {
			likedByUsers.add(userEmail);
		}
	}
	
	public Integer getLikes() {
		if (likedByUsers != null) {
			return likedByUsers.size();
		} else {
			return 0;
		}
	}

}
