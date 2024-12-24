package com.sparta.popupstore.domain.oauth2.client.kakao.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;

public class KakaoUserInfoResponse extends OAuth2UserInfo {

    @JsonSetter("id")
    public void setUserInfo(String id) {
        this.platform = OAuth2Platform.KAKAO;
        this.platformId = id;
    }
}
