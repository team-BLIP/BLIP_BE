package com.example.blip_be.domain.team.domain.repository;

import com.example.blip_be.domain.team.domain.Team;
import com.example.blip_be.domain.team.domain.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    /**
 * Retrieves a team member associated with the given team.
 *
 * @param team the team object for which to find the corresponding member
 * @return an Optional containing the found team member, or an empty Optional if none exists
 */
Optional<TeamMember> findById(Team team);

    /**
 * Deletes all TeamMember entities associated with the specified team ID.
 *
 * @param teamId the identifier of the team whose associated team members should be deleted
 */
void deleteByTeamId(Long teamId);
}
