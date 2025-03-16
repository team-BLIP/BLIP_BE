package com.example.blip_be.domain.meeting.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinMeetingRequest {

    private Long meetingId;
    private Long userId;
}
