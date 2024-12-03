package com.sparta.popupstore.domain.user.dto.request;

import lombok.Getter;

@Getter
public class UserSigninRequestDto {
    private String email;
    private String password;
}
