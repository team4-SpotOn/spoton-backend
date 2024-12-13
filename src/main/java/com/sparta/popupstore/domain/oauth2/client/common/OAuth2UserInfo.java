package com.sparta.popupstore.domain.oauth2.client.common;

import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2UserInfo {

    private OAuth2Platform platform;
    private String platformId;
}
