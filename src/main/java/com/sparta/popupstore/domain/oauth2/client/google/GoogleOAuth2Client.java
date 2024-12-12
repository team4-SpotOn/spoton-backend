package com.sparta.popupstore.domain.oauth2.client.google;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2Client;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.client.common.TokenResponse;
import com.sparta.popupstore.domain.oauth2.client.google.dto.GoogleUserInfoResponse;
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
public class GoogleOAuth2Client implements OAuth2Client {

    private final static String AUTH_SERVER_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private final static String TOKEN_SERVER_URL = "https://oauth2.googleapis.com/token";
    private final static String RESOURCE_SERVER_URL = "https://www.googleapis.com/userinfo/v2/me";

    @Value("${oauth2.google.client_id}")
    private String clientId;
    @Value("${oauth2.google.client_secret}")
    private String clientSecret;
    @Value("${oauth2.google.redirect_url}")
    private String redirectUri;
    @Value("${oauth2.google.scope}")
    private String scope;

    private final RestClient restClient;

    @Override
    public String generateSigninPageUrl() {
        return AUTH_SERVER_URL
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&scope=" + scope
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
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));
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
                        .body(GoogleUserInfoResponse.class)
        ).orElseThrow(() -> new CustomApiException(ErrorCode.SOCIAL_USERINFO_ERROR));
    }

    @Override
    public boolean supports(OAuth2Provider provider) {
        return provider == OAuth2Provider.GOOGLE;
    }
}
