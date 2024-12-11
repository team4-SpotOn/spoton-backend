package com.sparta.popupstore.domain.company.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CompanyUpdateRequestDto {

    @NotBlank(message = "회사 전화번호를 입력해 주세요.")
    private String phone;
    @NotBlank(message = "회사 사이트를 입력해 주세요.")
    private String website;
}
