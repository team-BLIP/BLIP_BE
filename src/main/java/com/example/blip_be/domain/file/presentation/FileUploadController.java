package com.example.blip_be.domain.file.presentation;

import com.example.blip_be.domain.file.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/voice")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadVoice(@RequestParam("file")MultipartFile file) {
        fileUploadService.uploadVoiceFile(file);
        return "음성 파일 업로드 성공";
    }

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        fileUploadService.uploadImageFile(file);
        return "이미지 업로드 성공";
    }
}
