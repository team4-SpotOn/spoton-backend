package com.sparta.popupstore.domain.oauth2.client.naver;

import com.sparta.popupstore.domain.oauth2.client.common.OAuth2Client;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Getter
public class NaverOAuth2Client extends OAuth2Client {

    @Value("${oauth2.naver.client_id}")
    private String clientId;
    @Value("${oauth2.naver.client_secret}")
    private String clientSecret;
    @Value("${oauth2.naver.redirect_url}")
    private String redirectUri;
    @Value("")
    private String scope;

    public NaverOAuth2Client(RestClient restClient) {
        super(restClient);
        AUTH_SERVER_URL = "https://nid.naver.com/oauth2.0/authorize";
        TOKEN_SERVER_URL = "https://nid.naver.com/oauth2.0/token";
        RESOURCE_SERVER_URL = "https://openapi.naver.com/v1/nid/me";
        platform = OAuth2Platform.NAVER;
    }

}
