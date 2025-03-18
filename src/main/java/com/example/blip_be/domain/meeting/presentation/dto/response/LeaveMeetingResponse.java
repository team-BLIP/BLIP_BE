package com.example.blip_be.domain.meeting.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LeaveMeetingResponse {

    private Long meetingId;
    private Long userId;
    private String message;
}
