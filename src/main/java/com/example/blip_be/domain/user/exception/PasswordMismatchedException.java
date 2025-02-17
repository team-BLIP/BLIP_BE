package com.example.blip_be.domain.user.exception;

import com.example.blip_be.global.error.CustomException;
import com.example.blip_be.global.error.ErrorCode;

public class PasswordMismatchedException extends CustomException {

    public static final CustomException EXCEPTION = new PasswordMismatchedException();

    private PasswordMismatchedException() {
        super(ErrorCode.PASSWORD_MISMATCHED);
    }
}
