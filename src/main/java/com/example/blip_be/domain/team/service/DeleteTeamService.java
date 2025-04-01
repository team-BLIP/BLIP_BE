package com.example.blip_be.domain.team.service;

import com.example.blip_be.domain.team.TeamNotFoundException;
import com.example.blip_be.domain.team.domain.repository.TeamMemberRepository;
import com.example.blip_be.domain.team.domain.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteTeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;

    @Transactional
    public void delete(Long teamId) {

        if (!teamRepository.existsById(teamId)) {
            throw TeamNotFoundException.EXCEPTION;
        }

        teamMemberRepository.deleteByTeamId(teamId);

        teamRepository.deleteById(teamId);
    }
}
