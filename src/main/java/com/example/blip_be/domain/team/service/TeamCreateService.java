package com.example.blip_be.domain.team.service;

import com.example.blip_be.domain.team.domain.Team;
import com.example.blip_be.domain.team.domain.TeamMember;
import com.example.blip_be.domain.team.domain.repository.TeamMemberRepository;
import com.example.blip_be.domain.team.domain.repository.TeamRepository;
import com.example.blip_be.domain.team.presentation.dto.request.CreateTeamRequest;
import com.example.blip_be.domain.team.presentation.dto.response.CreateTeamResponse;
import com.example.blip_be.domain.user.domain.UserEntity;
import com.example.blip_be.domain.user.facade.UserFacade;
import com.example.blip_be.global.enums.Role;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamCreateService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final UserFacade userFacade;
    private static final Logger logger = LoggerFactory.getLogger(TeamCreateService.class);

    @Transactional
    public CreateTeamResponse createTeam(CreateTeamRequest request) {
        logger.info("현재 사용자: {}", userFacade.getCurrentUser());
        UserEntity user = userFacade.getCurrentUser();

        user.updateRole(Role.LEADER);
        updateAuthentication(user);


        String inviteLink = UUID.randomUUID().toString();

        Team team = teamRepository.save(
                Team.builder()
                        .teamName(request.getTeamName())
                        .leader(user)
                        .inviteLink(inviteLink)
                        .build()
        );

        teamMemberRepository.save(
                TeamMember.builder()
                        .team(team)
                        .user(user)
                        .nickname(request.getNickName())
                        .build()
        );
        return CreateTeamResponse.builder()
                .teamId(team.getId())
                .teamName(team.getTeamName())
                .teamLeader(team.getLeader().getEmail())
                .inviteLink(team.getInviteLink())
                .build();
    }

    private void updateAuthentication(UserEntity user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UsernamePasswordAuthenticationToken newAuth =
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        authentication.getCredentials(),
                        user.getRole().getAuthorities()
                );

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
