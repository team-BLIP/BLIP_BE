package com.example.blip_be.domain.team.domain;

import com.example.blip_be.domain.user.domain.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String nickname;

    @Builder
    public TeamMember(Team team, UserEntity user, String nickname) {
        this.team = team;
        this.user = user;
        this.nickname = nickname;
    }
}