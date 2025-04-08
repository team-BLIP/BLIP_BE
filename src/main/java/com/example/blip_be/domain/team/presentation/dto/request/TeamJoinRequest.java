package com.example.blip_be.domain.team.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class TeamJoinRequest {

    private String inviteLink;
    private String nickName;
}
