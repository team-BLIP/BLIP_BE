package com.example.blip_be.domain.team.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamListResponse {
    private Long teamId;
    private String imageUrl;
}
