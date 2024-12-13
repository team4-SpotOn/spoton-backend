package com.sparta.popupstore.domain.oauth2.client.google.dto;

import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GoogleUserInfoResponse extends OAuth2UserInfo {

    public GoogleUserInfoResponse(String id) {
        super(OAuth2Platform.GOOGLE, id);
    }
}
