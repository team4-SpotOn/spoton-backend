package com.sparta.popupstore.domain.user.dto.response;

import com.sparta.popupstore.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserMypageResponseDto {

    private String email;
    private String name;

    public UserMypageResponseDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
    }
}
