package com.sparta.popupstore.domain.oauth2.client.google.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GoogleUserInfoResponse extends OAuth2UserInfo {

    public GoogleUserInfoResponse(String id, String email) {
        super(OAuth2Provider.GOOGLE, id, email);
    }
}
