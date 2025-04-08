package com.example.blip_be.domain.meeting.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MeetingResponse {

    private String summary;
    private String feedback;
}