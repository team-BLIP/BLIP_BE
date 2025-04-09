package com.example.blip_be.domain.meeting.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MeetingFeedbackResponse {
    private Long meetingId;
    private String feedback;
}
