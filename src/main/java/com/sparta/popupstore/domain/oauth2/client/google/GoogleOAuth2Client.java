package com.sparta.popupstore.domain.oauth2.client.google;

import com.sparta.popupstore.domain.oauth2.client.common.OAuth2Client;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class GoogleOAuth2Client implements OAuth2Client {

    private final RestClient restClient;

    @Override
    public String generateSigninPageUrl() {
        return "";
    }

    @Override
    public String getAccessToken(String authorizationCode) {
        return "";
    }

    @Override
    public OAuth2UserInfo getUserInfo(String accessToken) {
        return null;
    }

    @Override
    public boolean supports(OAuth2Provider provider) {
        return provider == OAuth2Provider.GOOGLE;
    }
}
