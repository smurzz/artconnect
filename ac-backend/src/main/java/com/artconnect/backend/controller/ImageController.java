package com.artconnect.backend.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.artconnect.backend.service.ImageService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/images")
public class ImageController {

	private final ImageService imageService;

	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Mono<ResponseEntity<byte[]>> addPhoto(
			@RequestPart Mono<FilePart> file, 
			@RequestHeader("Content-Length") Long fileSize) {
		return imageService.addPhoto(file, fileSize)
				.map(image -> {
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.valueOf(image.getContentType()));
					headers.set("Content-Disposition", "attachment; filename=" + image.getTitle());

					return ResponseEntity.ok().headers(headers).body(image.getImage().getData());
		});
	}

	@GetMapping("/{id}")
	public Mono<ResponseEntity<byte[]>> getPhoto(@PathVariable String id) {
		return imageService.getPhoto(id)
				.map(image -> {
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.valueOf(image.getContentType()));
					headers.set("Content-Disposition", "attachment; filename=" + image.getTitle());
		
					return ResponseEntity.ok().headers(headers).body(image.getImage().getData());
		});
	}

}
