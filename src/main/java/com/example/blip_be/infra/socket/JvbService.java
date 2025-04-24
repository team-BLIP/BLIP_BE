package com.example.blip_be.global.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JvbService {

    private static final Logger logger = LoggerFactory.getLogger(JvbService.class);

    private final RestTemplate restTemplate = new RestTemplate();

    public void forwardToJvb(String message, String type) {
        String url = "https://localhost:8080/" + type + "/" + message;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(message, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            logger.info("JVB 응답: {}", response.getBody());
        } catch (Exception e) {
            logger.error("JVB 메세지 전달 실패: {}", e.getMessage());
        }
    }
}
