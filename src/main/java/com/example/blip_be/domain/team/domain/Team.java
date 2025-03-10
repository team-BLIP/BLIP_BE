package com.example.blip_be.domain.team.domain;

import com.example.blip_be.domain.user.domain.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;

    @OneToMany(mappedBy = "team")
    private List<TeamMember> members = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "leader_id", nullable = false)
    private UserEntity leader;

    private String inviteLink;

    @Builder
    public Team(String teamName, UserEntity leader, String inviteLink) {
        this.teamName = teamName;
        this.leader = leader;
        this.inviteLink = inviteLink;
    }
}