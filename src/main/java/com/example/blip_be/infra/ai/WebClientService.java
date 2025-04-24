package com.example.blip_be.infra.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebClientService {

    private final WebClient.Builder webClientBuilder;

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    public WebClient createWebClient(String baseUrl) {
        return webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<Result> analyzeMeeting(String s3Url) {
        WebClient webClient = createWebClient(aiServiceUrl);

        return webClient.post()
                .uri("/meeting")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new AIRequest(s3Url))
                .retrieve()
                .bodyToMono(Result.class);
    }
}