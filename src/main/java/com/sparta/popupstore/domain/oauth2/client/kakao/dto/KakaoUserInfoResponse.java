package com.sparta.popupstore.domain.oauth2.client.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoUserInfoResponse extends OAuth2UserInfo {

    public KakaoUserInfoResponse(Long id, KakaoUserPropertiesResponse properties) {
        super(OAuth2Provider.KAKAO, id.toString(), properties.getEmail());
    }
}
