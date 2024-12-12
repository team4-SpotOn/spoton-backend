package com.sparta.popupstore.domain.oauth2.client.naver;

import com.sparta.popupstore.domain.oauth2.client.common.OAuth2Client;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class NaverOAuth2Client implements OAuth2Client {

    private final static String AUTH_SERVER_URL = "https://nid.naver.com/oauth2.0/authorize";
    private final static String TOKEN_SERVER_URL = "https://nid.naver.com/oauth2.0/token";
    private final static String RESOURCE_SERVER_URL = "https://openapi.naver.com/v1/nid/me";

    @Value("${oauth2.naver.client_id}")
    private String clientId;
    @Value("${oauth2.naver.client_secret}")
    private String clientSecret;
    @Value("${oauth2.naver.redirect_url}")
    private String redirectUrl;

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
        return provider == OAuth2Provider.NAVER;
    }

    @Override
    public Object callbackTest(String authorizationCode) {
        return null;
    }
}
