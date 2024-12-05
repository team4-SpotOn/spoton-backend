package com.sparta.popupstore.domain.common.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // user error
    USER_(403, "로그인 할 수 없다."),

    // company error

    // popupStore error

    // event error
    PROMOTION_EVENT_NOT(403, "이벤트를 다시 한번 확인해주세요."),
    PROMOTION_EVENT_ALREADY(400, "이미 시작한 이벤트는 수정 및 삭제 할 수 없습니다."),
    // review error

    // coupon error
    COUPON_SOLD_OUT(400,"쿠폰이 모두 소진되었습니다."),
    COUPON_DUPLICATE_ISSUANCE(400, "이미 발급 받으신 쿠폰입니다."),

    ;
    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
