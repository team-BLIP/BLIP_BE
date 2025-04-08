package com.example.blip_be.domain.team.presentation.dto.response;

import com.example.blip_be.domain.meeting.presentation.dto.response.MeetingResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TeamDetailResponse {
    private Long teamId;
    private String teamName;
    private String imageUrl;
    private List<MeetingResponse> meetings;
}