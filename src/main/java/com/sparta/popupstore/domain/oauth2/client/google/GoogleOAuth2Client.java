package com.sparta.popupstore.domain.oauth2.client.google;

import com.sparta.popupstore.domain.oauth2.client.common.OAuth2Client;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Getter
public class GoogleOAuth2Client extends OAuth2Client {

    @Value("${oauth2.google.client_id}")
    private String clientId;
    @Value("${oauth2.google.client_secret}")
    private String clientSecret;
    @Value("${oauth2.google.redirect_url}")
    private String redirectUri;
    @Value("${oauth2.google.scope}")
    private String scope;

    public GoogleOAuth2Client(RestClient restClient) {
        super(restClient);
        AUTH_SERVER_URL = "https://accounts.google.com/o/oauth2/v2/auth";
        TOKEN_SERVER_URL = "https://oauth2.googleapis.com/token";
        RESOURCE_SERVER_URL = "https://www.googleapis.com/userinfo/v2/me";
        platform = OAuth2Platform.GOOGLE;
    }
}
