package com.example.blip_be.domain.team.presentation.dto.response;

import com.example.blip_be.domain.meeting.domain.MeetingSummary;
import com.example.blip_be.domain.team.domain.TeamMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class CreateTeamResponse {

    private final Long teamId;

    private final String teamName;

    private final String teamLeader;

    private final String nickname;

    private final LocalDateTime createAt;

    private final MeetingSummary meetingSummary;

    private final List<TeamMember> teamMember;

    private final String inviteLink;
}
