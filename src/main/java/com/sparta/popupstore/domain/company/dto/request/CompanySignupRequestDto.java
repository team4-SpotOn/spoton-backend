package com.sparta.popupstore.domain.company.dto.request;

import com.sparta.popupstore.domain.company.entity.Company;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CompanySignupRequestDto {
    @Email(message = "이메일을 입력해 주세요.")
    private String email;
    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "비밀번호는 최소 8자리 이상이며, 대소문자, 숫자, 특수문자를 각각 최소 1개 이상 포함해야 합니다."
    )
    private String password;
    @NotBlank(message = "ceo 이름을 입력해 주세요.")
    private String ceoName;
    @NotBlank(message = "회사명을 입력해 주세요.")
    private String name;
    @NotBlank(message = "회사 주소를 입력해 주세요.")
    private String address;
    @NotBlank(message = "회사 전화번호를 입력해 주세요.")
    private String phone;
    @NotBlank(message = "회사 사이트를 입력해 주세요.")
    private String website;
    @NotBlank(message = "사업자 등록 번호를 입력해 주세요.")
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
