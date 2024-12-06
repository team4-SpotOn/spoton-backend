package com.sparta.popupstore.domain.user.dto.response;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserUpdateResponseDto {
    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final Address address;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UserUpdateResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.address = user.getAddress();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
