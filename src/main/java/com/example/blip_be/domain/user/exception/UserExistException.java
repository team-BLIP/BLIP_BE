package com.example.blip_be.domain.user.exception;

import com.example.blip_be.global.error.CustomException;
import com.example.blip_be.global.error.ErrorCode;

public class UserExistException extends CustomException {

    public static final CustomException EXCEPTION = new UserExistException();

    private UserExistException() {
        super(ErrorCode.USER_EXIST);
    }
}
