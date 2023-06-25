package com.artconnect.backend.service;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.artconnect.backend.model.Image;
import com.artconnect.backend.repository.ImageRepository;
import com.artconnect.backend.validation.ImageValidation;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	public Mono<Image> addPhoto(Mono<FilePart> file, Long sizeFile)  {
		return Mono
	            .zip(file, file.flatMap(filePart -> DataBufferUtils.join(filePart.content())))
	            .flatMap(tuple -> {
	                FilePart filePart = tuple.getT1();
	                DataBuffer dataBuffer = tuple.getT2();

	                ImageValidation imageValidation = ImageValidation.builder()
	                        .contentType(filePart.headers().getContentType().toString())
	                        .fileName(filePart.filename())
	                        .size(sizeFile)
	                        .build();

	                if (imageValidation.validFile()) {
	                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
	                    dataBuffer.read(bytes);

	                    Binary imageRequestBytes = new Binary(BsonBinarySubType.BINARY, bytes);

	                    Image image = Image.builder()
	                            .image(imageRequestBytes)
	                            .title(filePart.filename())
	                            .contentType(filePart.headers().getContentType().toString())
	                            .build();

	                    return imageRepository.save(image)
	                            .thenReturn(image);
	                } else {
	                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image is not valid"));
	                }
	            });
    }
	
	public Mono<Image> getPhoto(String id) { 
        return imageRepository.findById(id)
        		.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

}
