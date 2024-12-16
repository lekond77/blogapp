package com.leon.blog.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class FileService {

	private String location = "src/main/resources/posts";
	private final Path rootLocation;
	
	public FileService() {

		if (location.trim().length() == 0) {
			throw new RuntimeException("File upload location can not be Empty.");
		}

		this.rootLocation = Paths.get(location);
	}

	public String storeFile(MultipartFile file) {
		try {

			if (file.isEmpty()) {
				return "";
			}

			String originalFileName = file.getOriginalFilename();
			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
			String uniqueFileName = UUID.randomUUID().toString() + extension;

			Path destinationFile = this.rootLocation.resolve(Paths.get(uniqueFileName)).normalize().toAbsolutePath();

			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {

				return "";
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}

			return ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/files/") 
					.path(uniqueFileName) 
					.toUriString();
		} catch (IOException e) {
			return "";
		}
	}

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.rootLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file: " + fileName);
            }
        } catch (Exception ex) {
            throw new RuntimeException("Could not read the file: " + fileName, ex);
        }
    }
}

