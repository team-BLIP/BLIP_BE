package com.example.blip_be.domain.meeting.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StartMeetingResponse {

    private Long meetingId;
    private LocalDateTime startTime;
    private String roomUrl;
}
