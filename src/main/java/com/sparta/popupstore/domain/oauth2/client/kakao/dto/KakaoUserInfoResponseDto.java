package com.sparta.popupstore.domain.oauth2.client.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoUserInfoResponseDto extends OAuth2UserInfo {

    public KakaoUserInfoResponseDto(Long id, KakaoUserPropertiesResponseDto userProperties) {
        super(OAuth2Provider.KAKAO, id.toString(), userProperties.getEmail());
    }
}
