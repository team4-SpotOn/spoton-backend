package com.sparta.popupstore.domain.oauth2.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2Client;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.entity.SocialUser;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OAuth2SigninService {

    private final List<OAuth2Client> clientList;
    private final SocialUserService socialUserService;

    public String generateSigninPageUrl(OAuth2Platform platform) {
        OAuth2Client client = getClient(platform);
        return client.generateSigninPageUrl();
    }

    public SocialUser signin(OAuth2Platform platform, String authorizationCode) {
        OAuth2Client client = getClient(platform);
        String accessToken = client.getAccessToken(authorizationCode);
        OAuth2UserInfo userInfo = client.getUserInfo(accessToken);
        return socialUserService.signupIfAbsent(userInfo);
    }

    public String getAccessToken(OAuth2Platform platform, String authorizationCode) {
        OAuth2Client client = getClient(platform);
        return client.getAccessToken(authorizationCode);
    }

    public OAuth2Client getClient(OAuth2Platform platform) {
        return clientList.stream()
                .filter(client -> client.supports(platform))
                .findFirst()
                .orElseThrow(() -> new CustomApiException(ErrorCode.UNKNOWN_PLATFORM));
    }
}
