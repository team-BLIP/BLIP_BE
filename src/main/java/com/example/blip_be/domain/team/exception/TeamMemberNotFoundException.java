package com.example.blip_be.domain.team.exception;

import com.example.blip_be.global.error.CustomException;
import com.example.blip_be.global.error.ErrorCode;

public class TeamMemberNotFoundException extends CustomException {

    public final static CustomException EXCEPTION = new TeamMemberNotFoundException();

    private TeamMemberNotFoundException() {
        super(ErrorCode.TEAM_MEMBER_NOT_FOUND);
    }
}
