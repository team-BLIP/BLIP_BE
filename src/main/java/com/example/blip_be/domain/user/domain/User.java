package com.example.blip_be.domain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String voiceDataPath;

    @Builder
    public User(String accountId, String password, String email, String voiceDataPath) {
        this.accountId = accountId;
        this.password = password;
        this.email = email;
        this.voiceDataPath = voiceDataPath;
    }
}
