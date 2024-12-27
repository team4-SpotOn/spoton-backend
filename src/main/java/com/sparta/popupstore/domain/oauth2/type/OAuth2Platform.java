package com.sparta.popupstore.domain.oauth2.type;

import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfoDto;
import com.sparta.popupstore.domain.oauth2.client.google.dto.GoogleUserInfoDto;
import com.sparta.popupstore.domain.oauth2.client.kakao.dto.KakaoUserInfoDto;
import com.sparta.popupstore.domain.oauth2.client.naver.dto.NaverUserInfoDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OAuth2Platform {
    KAKAO(KakaoUserInfoDto.class),
    GOOGLE(GoogleUserInfoDto.class),
    NAVER(NaverUserInfoDto.class),
    ;

    private final Class<? extends OAuth2UserInfoDto> oauth2UserInfoClass;

    public Class<? extends OAuth2UserInfoDto> getOAuth2UserInfoClass() {
        return oauth2UserInfoClass;
    }
}
