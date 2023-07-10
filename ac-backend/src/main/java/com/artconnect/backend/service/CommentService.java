package com.artconnect.backend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.controller.request.CommentRequest;
import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.model.artwork.Comment;
import com.artconnect.backend.repository.ArtWorkRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CommentService {
	
	private final ArtWorkRepository artWorkRepository;
	
	private final ArtWorkService artWorkService;
	
	private final UserService userService;
	
	public Mono<ArtWork> create(String artworkId, CommentRequest commentRequest) {
		return userService.getCurrentUser()
				.flatMap(user -> {
					return artWorkService.findById(artworkId)
							.flatMap(artwork -> {
								String commentId = UUID.randomUUID().toString();
								Comment comment = Comment.builder()
										.id(commentId)
										.authorId(user.getId())
										.authorName(user.getFirstname())
										.createdAt(new Date())
										.isUpdated(false)
										.text(commentRequest.getCommentText())
										.build();
								List<Comment> comments = artwork.getComments() != null ? artwork.getComments() : new ArrayList<>();
								comments.add(comment);
								artwork.setComments(comments);
								return artWorkRepository.save(artwork);
							});
				});
	}
	
	public Mono<ArtWork> update(String artworkId, String commentId, CommentRequest commentRequest) {
	    return userService.getCurrentUser()
	            .flatMap(user -> artWorkService.findById(artworkId)
	                    .flatMap(artwork -> {
	                        List<Comment> comments = artwork.getComments();
	                        if (comments != null) {
	                            for (Comment comment : comments) {
	                                if (comment.getId().equals(commentId)) {
	                                    if (comment.getAuthorId().equals(user.getId())) {
	                                        comment.setText(commentRequest.getCommentText());
	                                        comment.setUpdated(true);
	                                        comment.setUpdatedAt(new Date());
	                                        return artWorkRepository.save(artwork);
	                                    } else {
	                                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to update a comment for another user"));
	                                    }
	                                }
	                            }
	                        }
	                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment is not found"));
	                    })
	                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment is not found")))
	            );
	}

	
	public Mono<Void> delete(String artworkId, String commentId) {
		 return userService.getCurrentUser()
		            .flatMap(user -> artWorkService.findById(artworkId)
		                    .flatMap(artwork -> {
		                        List<Comment> comments = artwork.getComments();
		                        if (comments != null) {
		                            for (Comment comment : comments) {
		                                if (comment.getId().equals(commentId)) {
		                                    if (comment.getAuthorId().equals(user.getId())) {
		                                    	comments.remove(comment);
												artwork.setComments(comments);
		                                        return artWorkRepository.save(artwork)
		                                        		.then();
		                                    } else {
		                                        return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete a comment for another user"));
		                                    }
		                                }
		                            }
		                        }
		                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment is not found"));
		                    })
		            );
	}

}
