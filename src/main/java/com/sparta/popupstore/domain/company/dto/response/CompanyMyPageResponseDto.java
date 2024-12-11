package com.sparta.popupstore.domain.company.dto.response;

import com.sparta.popupstore.domain.company.entity.Company;
import lombok.Getter;

@Getter
public class CompanyMyPageResponseDto {
    private final String name;
    private final String ceoName;
    private final String phone;

    public CompanyMyPageResponseDto(Company company) {
        this.name = company.getName();
        this.ceoName = company.getCeoName();
        this.phone = company.getPhone();
    }
}
