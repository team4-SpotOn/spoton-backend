package com.sparta.popupstore.domain.oauth2.client.google.dto;

import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;

public class GoogleUserInfoResponse extends OAuth2UserInfo {

    public GoogleUserInfoResponse(String id, Object email) {
        super(OAuth2Platform.GOOGLE, id);
    }
}
