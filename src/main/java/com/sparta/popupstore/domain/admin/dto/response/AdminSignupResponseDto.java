package com.sparta.popupstore.domain.admin.dto.response;

import com.sparta.popupstore.domain.admin.entity.Admin;
import lombok.Getter;

@Getter
public class AdminSignupResponseDto {
    private final Long id;
    private final String signinId;

    public AdminSignupResponseDto(Admin admin) {
        this.id = admin.getId();
        this.signinId = admin.getSigninId();
    }
}
