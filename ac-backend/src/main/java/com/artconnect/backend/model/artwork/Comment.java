package com.artconnect.backend.model.artwork;

import com.artconnect.backend.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Comment {
	
	private User author;
	
	private Data createdAt;
	
	private String text;

}
