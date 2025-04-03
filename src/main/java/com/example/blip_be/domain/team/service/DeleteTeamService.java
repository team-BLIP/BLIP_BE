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

    /**
     * Deletes a team and all of its members.
     *
     * <p>This method verifies that a team with the specified identifier exists. If the team is not found,
     * it throws a TeamNotFoundException. Otherwise, it removes all associated team members before deleting the team.</p>
     *
     * @param teamId the unique identifier of the team to be deleted
     */
    @Transactional
    public void delete(Long teamId) {

        if (!teamRepository.existsById(teamId)) {
            throw TeamNotFoundException.EXCEPTION;
        }

        teamMemberRepository.deleteByTeamId(teamId);

        teamRepository.deleteById(teamId);
    }
}
