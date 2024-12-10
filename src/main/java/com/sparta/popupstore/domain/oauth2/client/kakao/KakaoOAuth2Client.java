package com.sparta.popupstore.domain.oauth2.client.kakao;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.client.kakao.dto.KakaoTokenResponse;
import com.sparta.popupstore.domain.oauth2.client.kakao.dto.KakaoUserInfoResponseDto;
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
public class KakaoOAuth2Client {

    private final static String AUTH_SERVER_BASE_URL = "https://kauth.kakao.com";
    private final static String RESOURCE_SERVER_BASE_URL = "https://kapi.kakao.com";

    @Value("${oauth2.kakao.client_id}")
    private String clientId;
    @Value("${oauth2.kakao.redirect_url}")
    private String redirectUrl;

    private final RestClient restClient;

    public String generateSigninPageUrl() {
        return AUTH_SERVER_BASE_URL
                + "/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUrl
                + "&response_type=" + "code";
    }

    public String getAccessToken(String authorizationCode) {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("code", authorizationCode);

        return Optional.ofNullable(
                        restClient.post()
                                .uri(AUTH_SERVER_BASE_URL + "/oauth/token")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .body(body)
                                .retrieve()
                                .onStatus(HttpStatusCode::isError, (req, resp) -> {
                                    throw new CustomApiException(ErrorCode.SOCIAL_TOKEN_FAULT);
                                })
                                .body(KakaoTokenResponse.class)
                )
                .map(KakaoTokenResponse::accessToken)
                .orElseThrow(() -> new CustomApiException(ErrorCode.SOCIAL_TOKEN_FAULT));
    }

    public OAuth2UserInfo getUserInfo(String accessToken) {
        return Optional.ofNullable(
                restClient.get()
                        .uri(RESOURCE_SERVER_BASE_URL + "/v2/user/me")
                        .header("Authorization", "Bearer " + accessToken)
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, (req, resp) -> {
                            throw new CustomApiException(ErrorCode.SOCIAL_USERINFO_FAULT);
                        })
                        .body(KakaoUserInfoResponseDto.class)
        ).orElseThrow(() -> new CustomApiException(ErrorCode.SOCIAL_USERINFO_FAULT));
    }

}
