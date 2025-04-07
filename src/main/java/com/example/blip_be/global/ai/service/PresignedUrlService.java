package com.example.blip_be.global.ai.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PresignedUrlService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public URL generatePresignedUrl(String fileName) {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + 1000 * 60 * 10);

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        return amazonS3.generatePresignedUrl(request);
    }

    public URL generatePresignedDownloadUrl(String fileName) {
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + 1000 * 60 * 10);

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        return amazonS3.generatePresignedUrl(request);
    }
}