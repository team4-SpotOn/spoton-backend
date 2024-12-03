package com.sparta.popupstore.domain.company.dto.response;

import com.sparta.popupstore.domain.company.entity.Company;
import lombok.Getter;

@Getter
public class CompanySaveResponseDto {
    private final Long id;
    private final String email;
    private final String password;
    private final String ceoName;
    private final String name;
    private final String address;
    private final String phone;
    private final String website;
    private final String businessLicense;

    public CompanySaveResponseDto(Company company) {
        this.id = company.getId();
        this.email = company.getEmail();
        this.password = company.getPassword();
        this.ceoName = company.getCeoName();
        this.name = company.getName();
        this.address = company.getAddress();
        this.phone = company.getPhone();
        this.website = company.getWebsite();
        this.businessLicense = company.getBusinessLicense();
    }
}
