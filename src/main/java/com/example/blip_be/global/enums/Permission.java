package com.example.blip_be.global.enums;

import lombok.Getter;

@Getter
public enum Permission {

    READ("READ_AUTHORITY"),
    CREATE("CREATE_AUTHORITY"),
    UPDATE("UPDATE_AUTHORITY"),
    DELETE("DELETE_AUTHORITY"),
    INVITE_TEAM_MEMBER("INVITE_TEAM_MEMBER"),
    MEETING_MANAGEMENT("MEETING_MANAGEMENT");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
