package com.sparta.popupstore.domain.oauth2.client.kakao.dto;

import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;

public class KakaoUserInfoResponse extends OAuth2UserInfo {

    public KakaoUserInfoResponse(Long id, Object properties) {
        super(OAuth2Platform.KAKAO, id.toString());
    }
}
