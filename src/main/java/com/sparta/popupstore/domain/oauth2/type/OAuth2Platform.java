package com.sparta.popupstore.domain.oauth2.type;

import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.client.google.dto.GoogleUserInfoResponse;
import com.sparta.popupstore.domain.oauth2.client.kakao.dto.KakaoUserInfoResponse;
import com.sparta.popupstore.domain.oauth2.client.naver.dto.NaverUserInfoResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OAuth2Platform {
    KAKAO(KakaoUserInfoResponse.class),
    GOOGLE(GoogleUserInfoResponse.class),
    NAVER(NaverUserInfoResponse.class),
    ;

    private final Class<? extends OAuth2UserInfo> oauth2UserInfoClass;

    public Class<? extends OAuth2UserInfo> getOAuth2UserInfoClass() {
        return oauth2UserInfoClass;
    }
}
