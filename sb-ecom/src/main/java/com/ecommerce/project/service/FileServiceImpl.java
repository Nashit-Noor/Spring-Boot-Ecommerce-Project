package com.ecommerce.project.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {
	
	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		//file name of current/original file
		String originalFileName = file.getOriginalFilename();
		//generate a unique name
		String randomId = UUID.randomUUID().toString();
		
		//mat.jpg(file) -->> 1234(randomId) --> 1234.jpg(final unique filename)
		String fileName = randomId.concat(originalFileName).substring(originalFileName.lastIndexOf('.'));
		String filePath = path + File.separator + fileName;
		//check if path exist
		File folder = new File(path);
		if(!folder.exists()) {
			folder.mkdir();
		}
		//upload to server
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
		//returning filename
		return fileName;
	}
}
