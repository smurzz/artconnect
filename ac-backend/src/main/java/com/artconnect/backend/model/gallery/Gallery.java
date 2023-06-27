package com.artconnect.backend.model.gallery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "gallery")
public class Gallery {

	@Id
	private String id;

	@Indexed(unique = true)
	private String ownerId;

	private String ownerName;

	private String title;

	private String description;

	private List<String> artworkIds;

	private Set<GalleryCategory> categories;

	private Map<String, Integer> evaluations;

	public double getRating() {
		if (evaluations == null || evaluations.isEmpty()) {
			return 0.0;
		} else {
			int sum = evaluations.values().stream().mapToInt(Integer::intValue).sum();
			return (double) sum / evaluations.size();
		}
	}

	public void setEvaluation(String userEmail, Integer rating) {
		if (evaluations == null) {
			evaluations = new HashMap<>();
		}
		this.evaluations.put(userEmail, rating);
	}
}
