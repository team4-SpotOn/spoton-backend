package com.sparta.popupstore.domain.oauth2.client.naver;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2Client;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.client.common.TokenResponse;
import com.sparta.popupstore.domain.oauth2.client.naver.dto.NaverUserInfoResponse;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Optional;

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
    private String redirectUri;

    private final RestClient restClient;

    @Override
    public String generateSigninPageUrl() {
        return AUTH_SERVER_URL
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code";
    }

    @Override
    public String getAccessToken(String authorizationCode) {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("code", authorizationCode);

        return Optional.ofNullable(
                        restClient.post()
                                .uri(TOKEN_SERVER_URL)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .body(body)
                                .retrieve()
                                .onStatus(HttpStatusCode::isError, (req, resp) -> {
                                    throw new CustomApiException(ErrorCode.SOCIAL_TOKEN_ERROR);
                                })
                                .body(TokenResponse.class)
                )
                .map(TokenResponse::accessToken)
                .orElseThrow(() -> new CustomApiException(ErrorCode.SOCIAL_TOKEN_ERROR));
    }

    @Override
    public OAuth2UserInfo getUserInfo(String accessToken) {
        return Optional.ofNullable(
                restClient.get()
                        .uri(RESOURCE_SERVER_URL)
                        .header("Authorization", "Bearer " + accessToken)
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, (req, resp) -> {
                            throw new CustomApiException(ErrorCode.SOCIAL_USERINFO_ERROR);
                        })
                        .body(NaverUserInfoResponse.class)
        ).orElseThrow(() -> new CustomApiException(ErrorCode.SOCIAL_USERINFO_ERROR));
    }

    @Override
    public boolean supports(OAuth2Provider provider) {
        return provider == OAuth2Provider.NAVER;
    }

    @Override
    public Object callbackTest(String authorizationCode) {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", redirectUri);
        body.add("code", authorizationCode);

        return restClient.post()
                .uri(TOKEN_SERVER_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .body(TokenResponse.class);
    }
}
