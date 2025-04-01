package com.example.blip_be.domain.team.domain.repository;

import com.example.blip_be.domain.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findById(Long id);
    Optional<Team> findByInviteLink(String inviteLink);

    boolean existsById(Long id);

    void deleteById(Long id);
}