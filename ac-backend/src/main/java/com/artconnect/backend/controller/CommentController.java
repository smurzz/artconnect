package com.artconnect.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.artconnect.backend.controller.request.CommentRequest;
import com.artconnect.backend.controller.request.CommentUpdateRequest;
import com.artconnect.backend.controller.response.ArtWorkResponse;
import com.artconnect.backend.model.artwork.ArtWork;
import com.artconnect.backend.service.ArtWorkService;
import com.artconnect.backend.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/artworks/{id}/comments")
@RequiredArgsConstructor
@Validated
public class CommentController {

	private final CommentService commentService;
	
	private final ArtWorkService artWorkService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<ArtWorkResponse> createComment(
			@PathVariable String id,
			@Valid @RequestBody CommentRequest commentRequest) {
		return commentService.create(id, commentRequest).flatMap(this::mapArtWorkToResponse);
	}
	
	@PutMapping("/{commentId}")
	public Mono<ArtWorkResponse> updateComment(
			@PathVariable String id,
			@PathVariable String commentId,
			@Valid @RequestBody CommentUpdateRequest commentRequest) {
		return commentService.update(id, commentId, commentRequest).flatMap(this::mapArtWorkToResponse);
	}
	
	@DeleteMapping("/{commentId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> deleteComment(@PathVariable String id, @PathVariable String commentId) {
	    return commentService.delete(id, commentId);
	}
	
	private Mono<ArtWorkResponse> mapArtWorkToResponse(ArtWork artWork) {
        return artWorkService.mapArtWorkToResponse(artWork);
    }


}
