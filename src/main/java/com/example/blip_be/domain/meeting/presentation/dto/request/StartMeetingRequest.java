package com.example.blip_be.domain.meeting.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class StartMeetingRequest {

    private Long teamId;
    private String topic;
}
