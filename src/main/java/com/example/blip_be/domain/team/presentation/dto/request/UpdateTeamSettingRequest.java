package com.example.blip_be.domain.team.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateTeamSettingRequest {

    private String newName;
    private String imageUrl;
}
