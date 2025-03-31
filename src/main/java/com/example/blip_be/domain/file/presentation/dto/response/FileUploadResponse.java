package com.example.blip_be.domain.file.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileUploadResponse {

    private String message;
    private String filePath;
}
