package com.enviro.assessment.grad001.goodnesschauke.controller;


import com.enviro.assessment.grad001.goodnesschauke.entity.FileData;
import com.enviro.assessment.grad001.goodnesschauke.service.FileDataService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api")
public class FileDataController {

    @Autowired
    private FileDataService fileDataService;


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            FileData fileData = fileDataService.storeFile(file);
            return ResponseEntity.ok(new UploadFileResponse(fileData.getId(), "File uploaded successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file: " + e.getMessage());
        }
    }

    @GetMapping("/results/{fileId}")
    public ResponseEntity<?> getFileData(@PathVariable Long fileId) {
        try {
            FileData fileData = fileDataService.getFileData(fileId);
            return ResponseEntity.ok(fileData.getProcessedData());
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class UploadFileResponse {
        private Long fileId;
        private String message;
    }

}
