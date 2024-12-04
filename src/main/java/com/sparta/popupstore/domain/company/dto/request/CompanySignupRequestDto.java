package com.sparta.popupstore.domain.company.dto.request;

import com.sparta.popupstore.domain.company.entity.Company;
import lombok.Getter;

@Getter
public class CompanySignupRequestDto {
    private String email;
    private String password;
    private String ceoName;
    private String name;
    private String address;
    private String phone;
    private String website;
    private String businessLicense;

    public Company toEntity() {
        return Company.builder()
                .email(this.email)
                .password(this.password)
                .ceoName(this.ceoName)
                .name(this.name)
                .address(this.address)
                .phone(this.phone)
                .website(this.website)
                .businessLicense(this.businessLicense)
                .build();
    }
}
