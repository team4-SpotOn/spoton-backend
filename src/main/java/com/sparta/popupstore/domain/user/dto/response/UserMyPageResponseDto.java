package com.sparta.popupstore.domain.user.dto.response;

import com.sparta.popupstore.domain.user.entity.User;
import lombok.Getter;

@Getter
public class UserMyPageResponseDto {

    private final String email;
    private final String name;
    private final String address;

    public UserMyPageResponseDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.address = user.getAddress();
    }
}
