package com.example.blip_be.domain.file.exception;

import com.example.blip_be.global.error.CustomException;
import com.example.blip_be.global.error.ErrorCode;

public class FailedFileUploadException extends CustomException {

    public static final CustomException EXCEPTION = new FailedFileUploadException();

    private FailedFileUploadException() {
        super(ErrorCode.FAILED_FILE_UPLOAD);
    }
}
