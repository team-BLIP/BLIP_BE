package com.example.blip_be.domain.user.exception;

import com.example.blip_be.global.error.CustomException;
import com.example.blip_be.global.error.ErrorCode;

public class UserNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new UserNotFoundException();

    private UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
