package com.example.blip_be.domain.team;

import com.example.blip_be.global.error.CustomException;
import com.example.blip_be.global.error.ErrorCode;

public class TeamNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new TeamNotFoundException();

    private TeamNotFoundException() {
        super(ErrorCode.TEAM_NOT_FOUND);
    }
}
