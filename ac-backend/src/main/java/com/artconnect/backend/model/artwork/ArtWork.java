package com.artconnect.backend.model.artwork;

import java.util.Date;
import java.util.List;

import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

import com.artconnect.backend.model.Image;

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
	
	private String title;
	
	private List<Image> images;
    
    private String description;
    
    private int yearOfCreation;
    
    private int likes;
    
    private List<String> materials;
    
    private Dimension dimension;
    
    private double price;
    
    private List<String> tags;
    
    private String location;
    
    private Date createdAt;

}
