package com.sparta.popupstore.domain.oauth2.client.google;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2Client;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.client.google.dto.GoogleTokenResponse;
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

    private final static String AUTH_SERVER_BASE_URL = "https://accounts.google.com";
    private final static String RESOURCE_SERVER_BASE_URL = "https://www.googleapis.com";

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
        return AUTH_SERVER_BASE_URL
                + "/o/oauth2/v2/auth"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&scope=" + scope
                + "&response_type=" + "code";
    }

    @Override
    public String getAccessToken(String authorizationCode) {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("scope", scope);
        body.add("code", authorizationCode);

        return Optional.ofNullable(
                        restClient.post()
                                .uri(AUTH_SERVER_BASE_URL + "/token")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .body(body)
                                .retrieve()
                                .onStatus(HttpStatusCode::isError, (req, resq) -> {
                                    throw new CustomApiException(ErrorCode.SOCIAL_TOKEN_ERROR);
                                })
                                .body(GoogleTokenResponse.class)
                )
                .map(GoogleTokenResponse::accessToken)
                .orElseThrow(() -> new CustomApiException(ErrorCode.SOCIAL_TOKEN_ERROR));
    }

    @Override
    public OAuth2UserInfo getUserInfo(String accessToken) {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("scope", scope);

        return Optional.ofNullable(
                restClient.get()
                        .uri(RESOURCE_SERVER_BASE_URL + "/userinfo/v2/me")
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
