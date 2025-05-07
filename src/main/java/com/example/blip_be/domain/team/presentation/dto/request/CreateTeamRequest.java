package com.example.blip_be.domain.team.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class  CreateTeamRequest {

    @NotNull(message = "3 ~ 20자까지 가능합니다.")
    private String teamName;

    private String nickname;
}
