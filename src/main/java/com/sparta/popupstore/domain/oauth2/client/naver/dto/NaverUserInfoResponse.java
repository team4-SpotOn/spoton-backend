package com.sparta.popupstore.domain.oauth2.client.naver.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;

import java.util.Map;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NaverUserInfoResponse extends OAuth2UserInfo {

    public NaverUserInfoResponse(Long id, Map<String, Object> attributes) {
        super(OAuth2Provider.NAVER, id.toString(), (String) attributes.get("email"));
    }
}
