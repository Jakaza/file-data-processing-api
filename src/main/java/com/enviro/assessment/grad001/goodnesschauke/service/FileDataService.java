package com.enviro.assessment.grad001.goodnesschauke.service;

import com.enviro.assessment.grad001.goodnesschauke.entity.FileData;
import com.enviro.assessment.grad001.goodnesschauke.repository.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class FileDataService {

    @Autowired
    private FileDataRepository fileDataRepository;

    public FileData storeFile(MultipartFile file) throws IOException {
        FileData fileData = new FileData();
        fileData.setFileName(file.getOriginalFilename());
        fileData.setFileContent(new String(file.getBytes()));

        String processedData = processFileContent(fileData.getFileContent());
        fileData.setProcessedData(processedData);

        return fileDataRepository.save(fileData);
    }

    public FileData getFileData(Long id) throws FileNotFoundException {
        return fileDataRepository.findById(id).orElseThrow(() -> new FileNotFoundException("File not found with id " + id));
    }

    private String processFileContent(String content) {


        return "Processed: " + content;
    }
}
