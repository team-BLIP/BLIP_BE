package com.example.blip_be.global.exception;

import com.example.blip_be.global.error.CustomException;
import com.example.blip_be.global.error.ErrorCode;

public class ExpiredTokenException extends CustomException {

    public static final CustomException EXCEPTION = new ExpiredTokenException();

    private ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
