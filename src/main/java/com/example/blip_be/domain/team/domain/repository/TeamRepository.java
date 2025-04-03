package com.example.blip_be.domain.team.domain.repository;

import com.example.blip_be.domain.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    /**
 * Retrieves a Team entity by its unique identifier.
 *
 * @param id the unique identifier of the Team entity to retrieve
 * @return an Optional containing the Team if found, or an empty Optional if not present
 */
Optional<Team> findById(Long id);
    /**
 * Retrieves the team associated with the specified invite link.
 *
 * @param inviteLink the unique invite link identifying a team
 * @return an Optional containing the matching Team if found; otherwise, an empty Optional
 */
Optional<Team> findByInviteLink(String inviteLink);

    /**
 * Determines whether a Team entity with the specified identifier exists.
 *
 * @param id the unique identifier of the Team to verify
 * @return true if a Team with the given id exists, false otherwise
 */
boolean existsById(Long id);

    /**
 * Deletes the Team entity associated with the specified identifier.
 *
 * @param id the unique identifier of the Team to delete
 */
void deleteById(Long id);
}