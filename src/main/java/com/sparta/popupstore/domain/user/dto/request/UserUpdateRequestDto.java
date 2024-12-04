package com.sparta.popupstore.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    @NotBlank(message = "주소를 입력해 주세요.")
    private String address;
}
