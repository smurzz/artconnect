package com.artconnect.backend.model.artwork;

import java.util.Date;

import com.artconnect.backend.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Comment {
	
	private String authorId;
	
	private String authorName;
	
	private Date createdAt;
	
	private String text;

}
