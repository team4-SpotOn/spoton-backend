package com.sparta.popupstore.domain.oauth2.client.common;

import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;

public interface OAuth2Client {

    String generateSigninPageUrl();

    String getAccessToken(String authorizationCode);

    OAuth2UserInfo getUserInfo(String accessToken);

    boolean supports(OAuth2Provider provider);

    Object callbackTest(String authorizationCode);
}
