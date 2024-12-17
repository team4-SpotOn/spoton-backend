package com.sparta.popupstore.domain.oauth2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ValidPhoneRequestDto {
    @NotBlank
    @Pattern(
            regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",
            message = "알맞은 핸드폰 번호를 입력하세요."
    )
    private String phone;
}
