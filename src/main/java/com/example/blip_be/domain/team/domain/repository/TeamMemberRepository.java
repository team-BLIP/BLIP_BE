package com.example.blip_be.domain.team.domain.repository;

import com.example.blip_be.domain.team.domain.Team;
import com.example.blip_be.domain.team.domain.TeamMember;
import com.example.blip_be.domain.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    Optional<TeamMember> findById(Team team);

    Optional<TeamMember> findByUserIdAndTeamId(Long userId, Long teamId);
}