package com.sparta.popupstore.domain.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // user error

    // company error

    // popupStore error
    IMAGE_SAVE_FAILURE(HttpStatus.NOT_FOUND, "이미지 저장에 실패했습니다."),
    POPUP_STORE_NOT_BY_THIS_COMPANY(HttpStatus.FORBIDDEN, "팝업스토어가 이 회사에서 만들어지지 않았습니다."),
    POPUP_STORE_ALREADY_START(HttpStatus.FORBIDDEN, "진행 전인 팝업스토어만 수정 가능합니다."),
    POPUP_STORE_NOT_FOUND(HttpStatus.BAD_REQUEST,"해당 팝업스토어가 없습니다."),
    POPUP_STORE_NOT_RESERVATION(HttpStatus.INTERNAL_SERVER_ERROR,"예약한 팝업스토어가 아닙니다."),
    // event error
    PROMOTION_EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이벤트가 없습니다."),
    PROMOTION_EVENT_ALREADY(HttpStatus.BAD_REQUEST, "이미 시작한 이벤트는 수정 및 삭제 할 수 없습니다."),
    PROMOTION_EVENT_NOT_START_AND_END_TIME(HttpStatus.BAD_REQUEST, "startDateTime 과 endDateTime 이 존재하지 않습니다."),
    PROMOTION_EVENT_END(HttpStatus.BAD_REQUEST, "이미 끝난 이벤트 입니다."),
    // review error
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND,"수정할 리뷰가 없습니다."),
    REVIEW_NOT_UPDATE(HttpStatus.FORBIDDEN,"작성자만 수정할 수 있습니다."),
    REVIEW_ALREADY_DELETED(HttpStatus.NOT_FOUND,"이미 삭제된 리뷰입니다."),
    REVIEW_CANT_DELETE(HttpStatus.BAD_REQUEST,"삭제권한이 없습니다."),
    // coupon error
    COUPON_SOLD_OUT(HttpStatus.BAD_REQUEST,"쿠폰이 모두 소진되었습니다."),
    COUPON_DUPLICATE_ISSUANCE(HttpStatus.BAD_REQUEST, "이미 발급 받으신 쿠폰입니다."),

    //auth error
    NEED_LOGIN(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    NOT_ADMIN(HttpStatus.FORBIDDEN, "접근할 수 없습니다."),
    ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입된 이메일입니다."),
    INCORRECT_EMAIL_OR_PASSWORD(HttpStatus.BAD_REQUEST, "이메일 또는 비밀번호가 잘못되었습니다."),
    PASSWORD_MISS_MATCH(HttpStatus.BAD_REQUEST, "비밀번호를 다시 입력해주세요."),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "로그인 토큰이 없습니다."),
    INVALID_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "잘못된 토큰 정보입니다."),
    UNKNOWN_PROVIDER(HttpStatus.BAD_REQUEST, "지원하지 않는 OAuth Provider 입니다."),
    SOCIAL_TOKEN_FAULT(HttpStatus.UNAUTHORIZED, "소셜 로그인 토큰 조회 실패"),
    SOCIAL_USERINFO_FAULT(HttpStatus.BAD_REQUEST, "소셜 로그인 유저 정보 조회 실패"),

    //image
    NOT_CORRECT_FORMAT(HttpStatus.BAD_REQUEST, "올바른 파일 형식이 아닙니다."),
    PRE_SIGNED_URL_FAIL(HttpStatus.UNAUTHORIZED, "preSingedUrl 생성 실패"),
    FAIL_DELETE_IMAGE_FILE(HttpStatus.UNAUTHORIZED, "이미지 파일 삭제에 실패했습니다."),
    NOT_CORRECT_URL_FORMAT(HttpStatus.BAD_REQUEST, "올바른 파일 이름이 아닙니다."),
    ;
    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
