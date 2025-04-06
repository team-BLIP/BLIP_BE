package com.example.blip_be.domain.file.presentation;

import com.example.blip_be.domain.file.presentation.dto.response.FileUploadResponse;
import com.example.blip_be.domain.file.service.FileUploadService;
import com.example.blip_be.global.ai.service.PresignedUrlService;
import com.example.blip_be.global.error.CustomException;
import com.example.blip_be.global.error.ErrorCode;
import com.example.blip_be.global.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;
    private final PresignedUrlService presignedUrlService;

    @PostMapping("/audio")
    @ResponseStatus(HttpStatus.CREATED)
    public FileUploadResponse uploadAudio(@RequestParam("file") MultipartFile file){
        String filePath = fileUploadService.uploadAudioFile(file);
        return new FileUploadResponse("음성 파일 업로드 성공", filePath);
    }

    @GetMapping("/presigned-url")
    public ResponseEntity<String> getPresignedUrl(@RequestParam String fileName) {
        URL presignedUrl = presignedUrlService.generatePresignedUrl(fileName);
        return ResponseEntity.ok(presignedUrl.toString());
    }

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    public FileUploadResponse uploadImage(@RequestParam("file") MultipartFile file) {
        String filePath = fileUploadService.uploadImageFile(file);
        return new FileUploadResponse("이미지 업로드 성공", filePath);
    }
}