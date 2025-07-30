package com.cpms.cmmn.exception;

import com.cpms.cmmn.response.ErrorCode;

public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // 예외 메시지로 자동 세팅
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
