package com.example.blip_be.domain.team.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.blip_be.domain.team.domain.Team;
import com.example.blip_be.domain.team.domain.repository.TeamRepository;
import com.example.blip_be.domain.team.presentation.dto.response.TeamDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamDetailService {

    private final TeamRepository teamRepository;

    public TeamDetailResponse getTeamDetail(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(()-> new NotFoundException("찾을 수 없는 팀"));

        return new TeamDetailResponse(team.getId(), team.getTeamName(), team.getImageUrl());
    }
}