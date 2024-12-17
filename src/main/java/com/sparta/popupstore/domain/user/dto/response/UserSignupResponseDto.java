package com.sparta.popupstore.domain.user.dto.response;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserSignupResponseDto {
    @Schema(description = "유저 고유번호")
    private final Long id;
    @Schema(description = "유저 이메일")
    private final String email;
    @Schema(description = "유저 이름")
    private final String name;
    @Schema(description = "유저 주소")
    private final Address address;
    @Schema(description = "유저 전화번호")
    private final String phone;
    @Schema(description = "생성일")
    private final LocalDateTime createdAt;
    @Schema(description = "수정일")
    private final LocalDateTime updatedAt;

    public UserSignupResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.address = user.getAddress();
        this.phone = user.getPhone();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
