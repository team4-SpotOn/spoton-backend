package com.sparta.popupstore.domain.oauth2.service;

import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.client.kakao.KakaoOAuth2Client;
import com.sparta.popupstore.domain.oauth2.entity.SocialUser;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2SigninService {

    private final KakaoOAuth2Client kakaoOAuth2Client;
    private final SocialUserService socialUserService;

    public String generateSigninPageUrl(OAuth2Provider provider) {
        return kakaoOAuth2Client.generateSigninPageUrl();
    }

    public SocialUser signin(OAuth2Provider provider, String authorizationCode) {
        String accessToken = kakaoOAuth2Client.getAccessToken(authorizationCode);
        OAuth2UserInfo userInfo = kakaoOAuth2Client.getUserInfo(accessToken);
        return socialUserService.signupIfAbsent(provider, userInfo);
    }
}
