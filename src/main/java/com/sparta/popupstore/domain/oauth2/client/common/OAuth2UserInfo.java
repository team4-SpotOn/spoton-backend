package com.sparta.popupstore.domain.oauth2.client.common;

import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuth2UserInfo {

    private OAuth2Provider provider;
    private Long providerId;
    private String email;
}
