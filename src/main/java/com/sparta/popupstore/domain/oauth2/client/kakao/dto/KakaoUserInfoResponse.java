package com.sparta.popupstore.domain.oauth2.client.kakao.dto;

import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class KakaoUserInfoResponse extends OAuth2UserInfo {

    public KakaoUserInfoResponse(Long id) {
        super(OAuth2Platform.KAKAO, id.toString());
    }
}
