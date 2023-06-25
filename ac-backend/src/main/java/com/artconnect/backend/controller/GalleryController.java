package com.artconnect.backend.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.artconnect.backend.controller.response.GalleryResponse;
import com.artconnect.backend.controller.response.UserResponse;
import com.artconnect.backend.model.gallery.Gallery;
import com.artconnect.backend.service.GalleryService;
import com.artconnect.backend.service.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/gallery")
@RequiredArgsConstructor
@Validated
public class GalleryController {
	
	private GalleryService galleryService;
	
	@GetMapping
	public Flux<GalleryResponse> getAllGalleries() {
		return null;
	}
	

}
