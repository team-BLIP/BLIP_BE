package com.example.blip_be.domain.file.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadService {

    private static final String voice = "/path/to/voice/files/";
    private static final String image = "/path/to/image/files/";

    public String uploadVoiceFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            Path path = Paths.get(voice + fileName);

            Path directory = path.getParent();
            if (directory != null && !Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            Files.write(path, file.getBytes());
            return path.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("음성 파일 업로드 실패");
        }
    }

    public void uploadImageFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            Path path = Paths.get(image + fileName);

            Path directory = path.getParent();
            if (directory != null && !Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            Files.write(path, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("이미지 업로드 실패");
        }
    }
}
