package com.example.blip_be.domain.team.service;

import com.example.blip_be.domain.team.TeamNotFoundException;
import com.example.blip_be.domain.team.domain.Team;
import com.example.blip_be.domain.team.domain.TeamMember;
import com.example.blip_be.domain.team.domain.repository.TeamMemberRepository;
import com.example.blip_be.domain.team.domain.repository.TeamRepository;
import com.example.blip_be.domain.team.presentation.dto.request.TeamJoinRequest;
import com.example.blip_be.domain.user.domain.UserEntity;
import com.example.blip_be.domain.user.facade.UserFacade;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamJoinService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserFacade userFacade;

    @Transactional
    public void joinTeam(TeamJoinRequest request) {
        UserEntity user = userFacade.getCurrentUser();

        Team team = teamRepository.findByInviteLink(request.getInviteLink())
                .orElseThrow(() -> TeamNotFoundException.EXCEPTION);

        TeamMember teamMember = TeamMember.builder()
                .team(team)
                .user(user)
                .nickname(user.getAccountId())
                .build();

        teamMemberRepository.save(teamMember);
    }
}
