package com.example.blip_be.domain.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.blip_be.domain.file.exception.FailedFileUploadException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final AmazonS3 AmazonS3Client;
    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.s3.audio-path}")
    private String audio;

    @Value("${cloud.aws.s3.image-path}")
    private String image;


    public String uploadFile(MultipartFile file, String filePath, String fileType) {
        if (file.isEmpty()) {
            throw FailedFileUploadException.EXCEPTION;
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith(fileType)) {
            throw FailedFileUploadException.EXCEPTION;
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String fileName = UUID.randomUUID() + "_" + FilenameUtils.getName(originalFilename);
            String key = filePath + fileName;
            
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            metadata.setContentLength(file.getSize());

            amazonS3Client.putObject(bucketName, key, file.getInputStream(), metadata);
            return key;
        } catch (IOException e) {
            throw FailedFileUploadException.EXCEPTION;
        }
    }

    public String uploadAudioFile(MultipartFile file) {
        return uploadFile(file, audio, "audio");
    }

    public String uploadImageFile(MultipartFile file) {
        return uploadFile(file, image, "image");
    }
}