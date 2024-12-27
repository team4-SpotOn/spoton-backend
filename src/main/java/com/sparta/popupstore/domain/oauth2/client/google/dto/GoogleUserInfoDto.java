package com.sparta.popupstore.domain.oauth2.client.google.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfoDto;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleUserInfoDto extends OAuth2UserInfoDto {

    private final OAuth2Platform platform = OAuth2Platform.GOOGLE;

    @JsonSetter("id")
    public void setPlatformId(String id) {
        this.platformId = id;
    }
}
