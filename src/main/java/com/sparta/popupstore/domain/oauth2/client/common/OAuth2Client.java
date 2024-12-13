package com.sparta.popupstore.domain.oauth2.client.common;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Objects;
import java.util.Optional;

@Getter
public abstract class OAuth2Client {

    private String AUTH_SERVER_URL;
    private String TOKEN_SERVER_URL;
    private String RESOURCE_SERVER_URL;

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String scope;

    private OAuth2Platform platform;
    private RestClient restClient;

    public String generateSigninPageUrl() {
        String signinPageUrl = getAUTH_SERVER_URL()
                + "?client_id=" + getClientId()
                + "&redirect_uri=" + getRedirectUri()
                + "&response_type=code";

        if(Objects.isNull(getScope())) {
            return signinPageUrl;
        }

        return signinPageUrl
                + "&scope=" + getScope();
    }

    public String getAccessToken(String authorizationCode) {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", getClientId());
        body.add("client_secret", getClientSecret());
        body.add("redirect_uri", getRedirectUri());
        body.add("code", authorizationCode);

        return Optional.ofNullable(
                        getRestClient().post()
                                .uri(getTOKEN_SERVER_URL())
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

    public abstract OAuth2UserInfo getUserInfo(String accessToken);

    public boolean supports(OAuth2Platform platform) {
        return getPlatform() == platform;
    }
}
