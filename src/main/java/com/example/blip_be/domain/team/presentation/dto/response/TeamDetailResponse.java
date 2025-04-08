package com.example.blip_be.domain.team.presentation.dto.response;

import com.example.blip_be.domain.team.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamDetailResponse {
    private Long teamId;
    private String teamName;
    private String imageUrl;
}
