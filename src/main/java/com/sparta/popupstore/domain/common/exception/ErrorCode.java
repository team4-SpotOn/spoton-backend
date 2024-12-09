package com.sparta.popupstore.domain.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // user error

    // company error

    // popupStore error

    // event error
    PROMOTION_EVENT_NOT(HttpStatus.FORBIDDEN, "이벤트를 다시 한번 확인해주세요."),
    PROMOTION_EVENT_ALREADY(HttpStatus.BAD_REQUEST, "이미 시작한 이벤트는 수정 및 삭제 할 수 없습니다."),
    PROMOTION_EVENT_NOT_START_AND_END_TIME(HttpStatus.BAD_REQUEST, "startDateTime 과 endDateTime 이 존재하지 않습니다."),
    // review error

    // coupon error
    COUPON_SOLD_OUT(HttpStatus.BAD_REQUEST,"쿠폰이 모두 소진되었습니다."),
    COUPON_DUPLICATE_ISSUANCE(HttpStatus.BAD_REQUEST, "이미 발급 받으신 쿠폰입니다."),

    //auth error
    NEED_LOGIN(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    NOT_ADMIN(HttpStatus.FORBIDDEN, "접근할 수 없습니다."),
    ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입된 이메일입니다."),
    INCORRECT_EMAIL_OR_PASSWORD(HttpStatus.BAD_REQUEST, "이메일 또는 비밀번호가 잘못되었습니다."),
    PASSWORD_MISS_MATCH(HttpStatus.BAD_REQUEST, "비밀번호를 다시 입력해주세요.")
    ;
    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
