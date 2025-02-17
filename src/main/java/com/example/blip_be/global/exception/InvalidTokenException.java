package com.example.blip_be.global.exception;

import com.example.blip_be.global.error.CustomException;
import com.example.blip_be.global.error.ErrorCode;

public class InvalidTokenException extends CustomException {

    public static final CustomException EXCEPTION = new InvalidTokenException();

    private InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
