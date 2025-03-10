package com.example.blip_be.global.enums;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Role {

    LEADER(Set.of(
            Permission.READ,
            Permission.CREATE,
            Permission.UPDATE,
            Permission.DELETE,
            Permission.MEETING_MANAGEMENT,
            Permission.INVITE_TEAM_MEMBER
    )),
    USER(Set.of(
            Permission.READ,
            Permission.CREATE
    ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
