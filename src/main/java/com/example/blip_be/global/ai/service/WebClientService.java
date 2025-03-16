package com.example.blip_be.global.ai.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebClientService {

    private final WebClient.Builder webClientBuilder;

    public WebClient createWebClient(String baseUrl) {
        return webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<Result> analyzeMeeting(String meetingId, String fileUrl) {
        WebClient webClient = createWebClient("http://192.168.1.60:8000/");

        return webClient.post()
                .uri("/analyze")
                .bodyValue(new AIRequest(meetingId, fileUrl))
                .retrieve()
                .bodyToMono(Result.class);
    }
}
