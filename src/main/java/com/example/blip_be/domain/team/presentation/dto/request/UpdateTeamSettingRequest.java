package com.example.blip_be.domain.team.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateTeamSettingRequest {

    private String newName;
}
