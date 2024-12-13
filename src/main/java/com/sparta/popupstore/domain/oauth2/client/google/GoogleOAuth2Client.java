package com.sparta.popupstore.domain.oauth2.client.google;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2Client;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.client.google.dto.GoogleUserInfoResponse;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
@Getter
@RequiredArgsConstructor
public class GoogleOAuth2Client extends OAuth2Client {

    private final String AUTH_SERVER_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private final String TOKEN_SERVER_URL = "https://oauth2.googleapis.com/token";
    private final String RESOURCE_SERVER_URL = "https://www.googleapis.com/userinfo/v2/me";

    @Value("${oauth2.google.client_id}")
    private String clientId;
    @Value("${oauth2.google.client_secret}")
    private String clientSecret;
    @Value("${oauth2.google.redirect_url}")
    private String redirectUri;
    @Value("${oauth2.google.scope}")
    private String scope;

    private final OAuth2Platform platform = OAuth2Platform.GOOGLE;

    private final RestClient restClient;

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
}
