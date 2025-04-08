package com.example.blip_be.domain.user.domain;

import com.example.blip_be.domain.meeting.domain.Meeting;
import com.example.blip_be.domain.team.domain.Team;
import com.example.blip_be.global.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_id")
    private Team main;

    @ManyToMany
    @JoinTable(
            name = "user_team",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teams = new ArrayList<>();

    @Builder
    public UserEntity(String accountId, String password, String email, Role role, String imageUrl) {
        this.accountId = accountId;
        this.password = password;
        this.email = email;
        this.role = role == null ? Role.USER : role;
        this.imageUrl = imageUrl;
    }

    public void updateRole(Role newRole) {
        this.role = newRole;
    }

    public void updateMain(Team team) {
        this.main = team;
    }
}
