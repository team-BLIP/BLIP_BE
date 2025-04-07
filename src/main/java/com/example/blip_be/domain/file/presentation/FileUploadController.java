package com.example.blip_be.domain.file.presentation;

import com.example.blip_be.global.ai.service.PresignedUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final PresignedUrlService presignedUrlService;

    @GetMapping("/presigned-url")
    public ResponseEntity<String> getPresignedUrl(@RequestParam String fileName) {
        URL presignedUrl = presignedUrlService.generatePresignedUrl(fileName);
        return ResponseEntity.ok(presignedUrl.toString());
    }

    @GetMapping("/download-url")
    public String generateDownloadUrl(@RequestParam("fileName") String fileName) {
        URL downloadUrl = presignedUrlService.generatePresignedDownloadUrl(fileName);
        return downloadUrl.toString();
    }
}