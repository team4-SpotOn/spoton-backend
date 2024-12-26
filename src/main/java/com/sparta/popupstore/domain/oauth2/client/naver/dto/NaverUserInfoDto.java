package com.sparta.popupstore.domain.oauth2.client.naver.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfoDto;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverUserInfoDto extends OAuth2UserInfoDto {

    private final OAuth2Platform platform = OAuth2Platform.NAVER;

    @JsonSetter("response")
    public void setPlatformId(NaverUserResponse response) {
        this.platformId = response.getId();
    }
}
