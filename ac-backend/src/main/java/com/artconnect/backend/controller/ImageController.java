package com.artconnect.backend.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.model.Image;
import com.artconnect.backend.service.ImageService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/images")
public class ImageController {

	private final ImageService imageService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Mono<ResponseEntity<byte[]>> addPhoto(
			@RequestPart Mono<FilePart> file, 
			@RequestHeader("Content-Length") Long fileSize) {
		return imageService.addPhoto(file)
				.map(image -> {
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.valueOf(image.getContentType()));
					headers.set("Content-Disposition", "attachment; filename=" + image.getTitle());

					return ResponseEntity.ok().headers(headers).body(image.getImage().getData());
		});
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/adds", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Mono<String> addPhotos(
	        @RequestPart Flux<FilePart> files) {

		 return imageService.addPhotos(files)
		            .collectList()
		            .map(images -> {
		                int imageNum = images.size();
		                return imageNum + " images uploaded successfully";
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
