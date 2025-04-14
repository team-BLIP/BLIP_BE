package com.example.blip_be.domain.team.service;

import com.example.blip_be.domain.team.domain.repository.TeamRepository;
import com.example.blip_be.domain.team.presentation.dto.request.TeamQueryRequest;
import com.example.blip_be.domain.team.presentation.dto.response.TeamListResponse;
import com.example.blip_be.domain.user.domain.UserEntity;
import com.example.blip_be.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamQueryService {

    private final TeamRepository teamRepository;
    private final UserFacade userFacade;

    public List<TeamListResponse> getMyTeams(TeamQueryRequest request) {

        UserEntity user = userFacade.getCurrentUser();
        return teamRepository.findByMembersUserId(user.getId()).stream()
                .map(team -> new TeamListResponse(team.getId(), team.getImageUrl()))
                .toList();
    }
}