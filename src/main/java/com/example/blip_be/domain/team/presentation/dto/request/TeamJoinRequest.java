package com.example.blip_be.domain.team.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class TeamJoinRequest {

    private String inviteLink;
    private String nickname;
}
