package com.example.blip_be.domain.team.service;

import com.example.blip_be.domain.team.domain.repository.TeamRepository;
import com.example.blip_be.domain.team.presentation.dto.response.TeamListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamQueryService {

    private final TeamRepository teamRepository;

    public List<TeamListResponse> getMyTeams(Long userId) {
        return teamRepository.findByMembersUserId(userId).stream()
                .map(team -> new TeamListResponse(team.getId(), team.getImageUrl()))
                .toList();
    }
}