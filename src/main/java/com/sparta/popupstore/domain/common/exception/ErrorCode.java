package com.sparta.popupstore.domain.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // user error
    USER_(403, "로그인 할 수 없다."),

    // company error

    // popupStore error

    // event error

    // review error

    // coupon error

    ;
    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
