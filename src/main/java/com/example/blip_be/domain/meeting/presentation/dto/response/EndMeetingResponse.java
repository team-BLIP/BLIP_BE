package com.example.blip_be.domain.meeting.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EndMeetingResponse {

    private LocalDateTime endTime;
    private String fileUrl;
    private String presignedUrl;
}