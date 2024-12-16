package com.sparta.popupstore.domain.oauth2.client.kakao;

import com.sparta.popupstore.domain.oauth2.client.common.OAuth2Client;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Getter
public class KakaoOAuth2Client extends OAuth2Client {

    @Value("${oauth2.kakao.client_id}")
    private String clientId;
//    @Value("${oauth2.kakao.client_secret}")
//    private String clientSecret;
    @Value("${oauth2.kakao.redirect_url}")
    private String redirectUri;
    @Value("")
    private String scope;

    public KakaoOAuth2Client(RestClient restClient) {
        super(restClient);
        AUTH_SERVER_URL = "https://kauth.kakao.com/oauth/authorize";
        TOKEN_SERVER_URL = "https://kauth.kakao.com/oauth/token";
        RESOURCE_SERVER_URL = "https://kapi.kakao.com/v2/user/me";
        platform = OAuth2Platform.KAKAO;
    }

}
