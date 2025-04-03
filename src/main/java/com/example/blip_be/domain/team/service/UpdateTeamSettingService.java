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

    /**
     * Updates the name of an existing team.
     *
     * <p>Validates that the new team name provided in the request is not null, retrieves the team by its ID, and updates its name.
     * The team's name is logged before and after the update. If the new name is null, an IllegalArgumentException is thrown;
     * if the team is not found, a TeamNotFoundException is thrown.
     *
     * @param teamId the identifier of the team to update
     * @param request an object containing the new team name
     * @throws IllegalArgumentException if the new team name is null
     * @throws TeamNotFoundException if no team exists with the provided teamId
     */
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