package com.artconnect.backend.validation;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ImageValidation {
	
	private final static long MAX_SIZE = 10485760;
	
	private final static List<String> EXTENSIONS = List.of("png", "jpg", "jpeg");
	
	private final long size;	
	private final String contentType;
	private final String fileName;
	
	public boolean validFile() {
		return validFileExtension() && validFileSize();
	}
	
	private boolean validFileSize() {
		return size <= MAX_SIZE;
	}
	
	private boolean validFileExtension() {
		if (!contentType.startsWith("image/")) {
            return false;
        }
		
		String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return EXTENSIONS.contains(fileExtension.toLowerCase());
	}
}
