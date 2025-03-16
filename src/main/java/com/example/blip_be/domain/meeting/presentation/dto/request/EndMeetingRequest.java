package com.example.blip_be.domain.meeting.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EndMeetingRequest {

    private Long meetingId;
    private Long leaderId;
}
