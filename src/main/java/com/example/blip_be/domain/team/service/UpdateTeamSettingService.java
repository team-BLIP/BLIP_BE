package com.example.blip_be.domain.team.service;

import com.example.blip_be.domain.team.TeamNotFoundException;
import com.example.blip_be.domain.team.domain.Team;
import com.example.blip_be.domain.team.domain.repository.TeamRepository;
import com.example.blip_be.domain.team.presentation.dto.request.UpdateTeamSettingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateTeamSettingService {

    private final TeamRepository teamRepository;

    @Transactional
    public void updateTeam(Long teamId, UpdateTeamSettingRequest request) {

        if (request.getNewName() == null) {
            throw new IllegalArgumentException("팀 이름은 비어있을 수 없습니다.");
        }

        Team team = teamRepository.findById(teamId)
                        .orElseThrow(()-> TeamNotFoundException.EXCEPTION);

        team.updateTeam(request.getNewName());
    }
}