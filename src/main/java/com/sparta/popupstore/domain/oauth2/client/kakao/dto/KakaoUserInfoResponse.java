package com.sparta.popupstore.domain.oauth2.client.kakao.dto;

import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;

public class KakaoUserInfoResponse extends OAuth2UserInfo {

    public KakaoUserInfoResponse(Long id, KakaoAccountResponse kakao_account) {
        super(OAuth2Provider.KAKAO, id.toString(), kakao_account.getEmail());
    }
}
