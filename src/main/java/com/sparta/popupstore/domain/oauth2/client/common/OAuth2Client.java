package com.sparta.popupstore.domain.oauth2.client.common;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public abstract class OAuth2Client {

    private final RestClient restClient;
    protected String AUTH_SERVER_URL;
    protected String TOKEN_SERVER_URL;
    protected String RESOURCE_SERVER_URL;
    protected OAuth2Platform platform;

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String scope;

    public String generateSigninPageUrl() {
        return AUTH_SERVER_URL
                + "?client_id=" + getClientId()
                + "&redirect_uri=" + getRedirectUri()
                + "&scope=" + getScope()
                + "&response_type=code";
    }

    public String getAccessToken(String authorizationCode) {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", getClientId());
        body.add("client_secret", getClientSecret());
        body.add("redirect_uri", getRedirectUri());
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
                .orElseThrow(() -> new CustomApiException(ErrorCode.SOCIAL_TOKEN_ERROR))
                .accessToken();
    }

    public OAuth2UserInfoDto getUserInfo(String accessToken) {
        return Optional.ofNullable(
                restClient.get()
                        .uri(RESOURCE_SERVER_URL)
                        .header("Authorization", "Bearer " + accessToken)
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, (req, resp) -> {
                            throw new CustomApiException(ErrorCode.SOCIAL_USERINFO_ERROR);
                        })
                        .body(platform.getOAuth2UserInfoClass())
        ).orElseThrow(() -> new CustomApiException(ErrorCode.SOCIAL_USERINFO_ERROR));
    }

    public boolean supports(OAuth2Platform platform) {
        return getPlatform() == platform;
    }
}
