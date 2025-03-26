package com.example.blip_be.domain.file.presentation;

import com.example.blip_be.domain.file.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/voice")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> uploadVoice(@RequestParam("file")MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어 있습니다.");
        }
        String filePath = fileUploadService.uploadVoiceFile(file);
        return Map.of("message", "음성 파일 업로드 성공", "filePath", filePath);
    }

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        fileUploadService.uploadImageFile(file);
        return "이미지 업로드 성공";
    }
}
