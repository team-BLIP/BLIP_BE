package com.example.blip_be.domain.meeting.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinMeetingResponse {

    private Long meetingId;
    private Long userId;
    private String status;
}
