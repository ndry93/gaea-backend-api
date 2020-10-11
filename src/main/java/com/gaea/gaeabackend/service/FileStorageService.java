/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.service;


import com.gaea.gaeabackend.common.FileStorageException;
import com.gaea.gaeabackend.common.MyFileNotFoundException;
import com.gaea.gaeabackend.config.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Value;

@Service
public class FileStorageService {
    
    @Value("${file.image-upload-dir}")
    private String uploadDir;
    
    
    private Path directoryHandler(String folderName) {
        String uploadDirTmp = this.uploadDir;
        if(folderName != null && !folderName.isEmpty()){
            uploadDirTmp = uploadDirTmp+"/"+folderName;
        }
        
        final Path fileStorageLocation =  Paths.get(uploadDirTmp).toAbsolutePath().normalize();
        
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (IOException ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
        return fileStorageLocation;
    }

    public String storeFile(MultipartFile file, String folderName) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = directoryHandler(folderName).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName, String folderName) {
        try {
            Path filePath = directoryHandler(folderName).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }
    
    public boolean deleteFileIfExist(String fileName, String folderName) {
        try {
            Path filePath = directoryHandler(folderName).resolve(fileName).normalize();
            return Files.deleteIfExists(filePath);
            
        } catch(Exception ie){
         throw new MyFileNotFoundException("File not found " + fileName);   
        }
    }
}