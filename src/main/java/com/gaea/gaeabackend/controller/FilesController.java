/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import com.gaea.gaeabackend.dto.UploadFileResponse;
import com.gaea.gaeabackend.service.FileStorageService;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author ndry93
 */
@RestController
@RequestMapping("/files")
public class FilesController {
    private static Logger logger = LoggerFactory.getLogger(FilesController.class);
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @PostMapping(path = {"/upload","/upload/{folderName}"})
    public UploadFileResponse uploadFile(@PathVariable(name = "folderName",required=false) String folderName,
            @RequestParam("file") MultipartFile file) {
        System.out.println("===== folderName "+folderName);
        String fileName = fileStorageService.storeFile(file,folderName);
        String fileDownloadUri;
        if(folderName != null && !folderName.isEmpty()){
            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download/")
                .path(folderName).path("/")
                .path(fileName)
                .toUriString();
        }else{
            fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download/")
                .path(fileName)
                .toUriString();
        }

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize(),folderName);
    }

    @PostMapping(path = {"/upload/many","/upload/many/{folderName}"})
    public List<UploadFileResponse> uploadMultipleFiles(@PathVariable(name = "folderName",required=false) String folderName,
            @RequestParam("file") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(folderName,file))
                .collect(Collectors.toList());
    }

    
    @GetMapping(path = {"/download/{folderName}/{fileName:.+}",
                            "/download/{fileName:.+}" })
    public ResponseEntity<Resource> downloadFile(@PathVariable(name = "folderName",required=false) String folderName,
            @PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName,folderName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
    @PostMapping(path = {"/delete/{folderName}/{fileName:.+}",
                "/delete/{fileName:.+}"})
    public boolean deleteFile(@PathVariable(name = "folderName",required=false) String folderName,
            @PathVariable String fileName) {
        // Load file as Resource
        System.out.println("=====filename "+fileName);
        boolean isDeleted = fileStorageService.deleteFileIfExist(fileName,folderName);
        return isDeleted;
    }
}
