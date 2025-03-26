package com.example.blip_be.domain.user.domain;

import com.example.blip_be.global.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


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


    @Builder
    public UserEntity(String accountId, String password, String email, Role role) {
        this.accountId = accountId;
        this.password = password;
        this.email = email;
        this.role = role == null ? Role.USER : role;
    }

    public void updateRole(Role newRole) {
        this.role = newRole;
    }
}
