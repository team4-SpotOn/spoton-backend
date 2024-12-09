package com.sparta.popupstore.domain.user.dto.response;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserSignupResponseDto {
    private final Long id;
    private final String email;
    private final String name;
    private final Address address;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UserSignupResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.address = user.getAddress();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
