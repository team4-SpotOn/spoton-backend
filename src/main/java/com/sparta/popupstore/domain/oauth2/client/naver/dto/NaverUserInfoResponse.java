package com.sparta.popupstore.domain.oauth2.client.naver.dto;

import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;

public class NaverUserInfoResponse extends OAuth2UserInfo {

    public NaverUserInfoResponse(NaverUserResponse response, Object message) {
        super(OAuth2Platform.NAVER, response.getId());
    }
}
