package com.sparta.popupstore.domain.common.exception;

import lombok.Getter;

@Getter
public class CustomApiException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
