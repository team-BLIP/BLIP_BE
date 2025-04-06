package com.example.blip_be.global.ai.service;

import com.example.blip_be.domain.file.service.FileUploadService;
import com.example.blip_be.domain.meeting.domain.Meeting;
import com.example.blip_be.domain.meeting.domain.repository.MeetingRepository;
import com.example.blip_be.domain.meeting.presentation.dto.request.EndMeetingRequest;
import com.example.blip_be.domain.meeting.presentation.dto.response.EndMeetingResponse;
import com.example.blip_be.domain.team.domain.Team;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WebClientService {

    private final WebClient.Builder webClientBuilder;

    public WebClient createWebClient(String baseUrl) {
        return webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<Result> analyzeMeeting(String s3Url) {
        WebClient webClient = createWebClient("http://3.35.180.21/");

        return webClient.post()
                .uri("/meeting")
                .bodyValue(new AIRequest(s3Url))
                .retrieve()
                .bodyToMono(Result.class);
    }
}