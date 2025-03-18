package com.example.blip_be.domain.meeting.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LeaveMeetingRequest {

    private Long meetingId;
    private Long userId;
}
