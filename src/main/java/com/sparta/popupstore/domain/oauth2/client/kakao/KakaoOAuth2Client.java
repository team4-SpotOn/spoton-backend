package com.sparta.popupstore.domain.oauth2.client.kakao;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2Client;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.client.kakao.dto.KakaoUserInfoResponse;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
@Getter
@RequiredArgsConstructor
public class KakaoOAuth2Client extends OAuth2Client {

    private final String AUTH_SERVER_URL = "https://kauth.kakao.com/oauth/authorize";
    private final String TOKEN_SERVER_URL = "https://kauth.kakao.com/oauth/token";
    private final String RESOURCE_SERVER_URL = "https://kapi.kakao.com/v2/user/me";

    @Value("${oauth2.kakao.client_id}")
    private String clientId;
    @Value("${oauth2.kakao.client_secret}")
    private String clientSecret;
    @Value("${oauth2.kakao.redirect_url}")
    private String redirectUri;

    private final OAuth2Provider provider = OAuth2Provider.KAKAO;

    private final RestClient restClient;

    @Override
    public OAuth2UserInfo getUserInfo(String accessToken) {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("property_keys", "[\"kakao_account.email\"]");

        return Optional.ofNullable(
                restClient.post()
                        .uri(RESOURCE_SERVER_URL)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .body(body)
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, (req, resp) -> {
                            throw new CustomApiException(ErrorCode.SOCIAL_USERINFO_ERROR);
                        })
                        .body(KakaoUserInfoResponse.class)
        ).orElseThrow(() -> new CustomApiException(ErrorCode.SOCIAL_USERINFO_ERROR));
    }
}
