package com.artconnect.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.artconnect.backend.config.jwt.JwtService;
import com.artconnect.backend.controller.response.GalleryResponse;
import com.artconnect.backend.model.gallery.Gallery;
import com.artconnect.backend.repository.GalleryRepository;
import com.artconnect.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GalleryService {
	
	public GalleryRepository galleryRepository;
	
	public GalleryResponse galleryResponse;
	
	public Flux<GalleryResponse> findAll() {
		
		return null;
	}

}
