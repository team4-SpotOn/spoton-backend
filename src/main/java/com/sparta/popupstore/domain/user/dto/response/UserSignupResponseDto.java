package com.sparta.popupstore.domain.user.dto.response;

import com.sparta.popupstore.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserSignupResponseDto {
    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String address;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UserSignupResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.address = user.getAddress();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
