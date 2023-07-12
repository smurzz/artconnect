package com.artconnect.backend.controller.request;

import com.artconnect.backend.validation.NotBlankIfSpecified;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class CommentUpdateRequest {

	@NotBlankIfSpecified
    private String commentText;
	
}

