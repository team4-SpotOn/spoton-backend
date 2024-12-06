package com.sparta.popupstore.domain.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiError {
    private final String msg;
    private final HttpStatus status;

    public ApiError(String msg, HttpStatus status) {
        this.msg = msg;
        this.status = status;
    }
}
