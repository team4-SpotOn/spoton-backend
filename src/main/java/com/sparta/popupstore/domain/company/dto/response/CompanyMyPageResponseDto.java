package com.sparta.popupstore.domain.company.dto.response;

import com.sparta.popupstore.domain.company.entity.Company;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CompanyMyPageResponseDto {
    @Schema(description = "회사명")
    private final String name;
    @Schema(description = "대표명")
    private final String ceoName;
    @Schema(description = "회사 전화번호")
    private final String phone;

    public CompanyMyPageResponseDto(Company company) {
        this.name = company.getName();
        this.ceoName = company.getCeoName();
        this.phone = company.getPhone();
    }
}
