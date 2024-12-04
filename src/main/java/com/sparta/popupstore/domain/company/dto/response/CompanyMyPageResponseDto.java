package com.sparta.popupstore.domain.company.dto.response;

import com.sparta.popupstore.domain.company.entity.Company;
import lombok.Getter;

@Getter
public class CompanyMyPageResponseDto {
    private String name;
    private String ceoName;
    private String address;
    private String phone;

    public CompanyMyPageResponseDto(Company company) {
        this.name = company.getName();
        this.ceoName = company.getCeoName();
        this.address = company.getAddress();
        this.phone = company.getPhone();
    }
}
