package com.example.blip_be.domain.team.domain;

import com.example.blip_be.domain.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<TeamMember> members = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "leader_id", nullable = false)
    private UserEntity leader;

    private String inviteLink;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Builder
    public Team(String teamName, UserEntity leader, String inviteLink) {
        this.teamName = teamName;
        this.leader = leader;
        this.inviteLink = inviteLink;
    }

    public void updateTeam(String teamName, String imageUrl) {
        this.teamName = teamName;
        this.imageUrl = imageUrl;
    }
}