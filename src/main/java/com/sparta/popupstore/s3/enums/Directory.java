package com.sparta.popupstore.s3.enums;

import lombok.Getter;

@Getter
public enum Directory {
    REVIEWS("review"),
    PROMOTION_EVENTS("promotion-event"),
    POPUP_STORES("popup-store")
    ;

    private final String domain;

    Directory(String domain) {
        this.domain = domain;
    }
}
