package com.artconnect.backend.service;

import java.util.List;

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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	public Mono<Image> addPhoto(Mono<FilePart> file)  {
		return Mono
	            .zip(file, file.flatMap(filePart -> DataBufferUtils.join(filePart.content())))
	            .flatMap(tuple -> {
	                FilePart filePart = tuple.getT1();
	                DataBuffer dataBuffer = tuple.getT2();
	                
	                Long sizeFile = Long.valueOf(dataBuffer.readableByteCount());
	                
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
	
	public Flux<Image> addPhotos(Flux<FilePart> files) {
		return files.flatMap(filePart ->  addPhoto(Mono.just(filePart)));    
	}
	
	public Mono<Image> getPhoto(String id) { 
        return imageRepository.findById(id)
        		.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
	
	public Flux<Image> getPhotosByIds(List<String> imageIds){
		return imageRepository.findAllById(imageIds);
	}
	
	public Mono<Void> deleteAllByIds(List<String> ids){
		return imageRepository.deleteAllById(ids)
				.then();
	}
	
	public Mono<Boolean> validedAllPhoto(Flux<FilePart> files) {
	    return files
	            .flatMap(file -> {
	                return Mono.zip(Mono.just(file), DataBufferUtils.join(file.content()))
	                        .flatMap(tuple -> {
	                            FilePart filePart = tuple.getT1();
	                            DataBuffer dataBuffer = tuple.getT2();

	                            Long sizeFile = Long.valueOf(dataBuffer.readableByteCount());

	                            ImageValidation imageValidation = ImageValidation.builder()
	                                    .contentType(filePart.headers().getContentType().toString())
	                                    .fileName(filePart.filename())
	                                    .size(sizeFile)
	                                    .build();

	                            if (imageValidation.validFile()) {
	                                return Mono.just(true);
	                            } else {
	                                return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image with name " + filePart.filename() + " is not valid"));
	                            }
	                        })
	                        .onErrorResume(error -> Mono.just(false));
	            })
	            .collectList()
	            .map(validations -> validations.stream().allMatch(valid -> valid));
	}

}
