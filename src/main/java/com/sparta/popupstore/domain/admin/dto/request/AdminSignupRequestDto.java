package com.sparta.popupstore.domain.admin.dto.request;

import com.sparta.popupstore.domain.admin.entity.Admin;
import lombok.Getter;

@Getter
public class AdminSignupRequestDto {
    private String signinId;
    private String password;

    public Admin toEntity(String encodedPassword) {
        return Admin.builder()
                .signinId(this.signinId)
                .password(encodedPassword)
                .build();
    }
}
