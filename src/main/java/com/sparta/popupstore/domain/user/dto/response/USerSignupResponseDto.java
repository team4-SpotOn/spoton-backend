package com.sparta.popupstore.domain.user.dto.response;

import com.sparta.popupstore.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class USerSignupResponseDto {
    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public USerSignupResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
