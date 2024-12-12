package com.sparta.popupstore.domain.oauth2.client.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoUserInfoResponse extends OAuth2UserInfo {

    // 카카오 로그인 정보
    public KakaoUserInfoResponse(Long id, KakaoAccountResponse kakao_account) {
        super(OAuth2Provider.KAKAO, id.toString(), kakao_account.getEmail());
    }
}
