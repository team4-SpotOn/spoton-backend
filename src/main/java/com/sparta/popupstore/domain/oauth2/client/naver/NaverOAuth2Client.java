package com.sparta.popupstore.domain.oauth2.client.naver;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2Client;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.client.naver.dto.NaverUserInfoResponse;
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
public class NaverOAuth2Client extends OAuth2Client {

    private final String AUTH_SERVER_URL = "https://nid.naver.com/oauth2.0/authorize";
    private final String TOKEN_SERVER_URL = "https://nid.naver.com/oauth2.0/token";
    private final String RESOURCE_SERVER_URL = "https://openapi.naver.com/v1/nid/me";

    @Value("${oauth2.naver.client_id}")
    private String clientId;
    @Value("${oauth2.naver.client_secret}")
    private String clientSecret;
    @Value("${oauth2.naver.redirect_url}")
    private String redirectUri;

    private final OAuth2Platform platform = OAuth2Platform.NAVER;

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
                        .body(NaverUserInfoResponse.class)
        ).orElseThrow(() -> new CustomApiException(ErrorCode.SOCIAL_USERINFO_ERROR));
    }
}
