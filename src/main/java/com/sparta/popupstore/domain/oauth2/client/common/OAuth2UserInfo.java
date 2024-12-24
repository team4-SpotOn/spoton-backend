package com.sparta.popupstore.domain.oauth2.client.common;

import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuth2UserInfo {

    protected OAuth2Platform platform;
    protected String platformId;
}
