package com.sparta.popupstore.domain.oauth2.client.naver.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;

public class NaverUserInfoResponse extends OAuth2UserInfo {

    @JsonSetter("response")
    public void setUserInfo(NaverUserResponse response) {
        this.platform = OAuth2Platform.NAVER;
        this.platformId = response.getId();
    }
}
