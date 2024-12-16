package com.sparta.popupstore.domain.user.dto.request;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserSignupRequestDto {
    @Email(message = "이메일을 입력해 주세요.")
    private String email;
    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "비밀번호는 최소 8자리 이상이며, 대소문자, 숫자, 특수문자를 각각 최소 1개 이상 포함해야 합니다."
    )
    private String password;
    @NotBlank(message = "이름을 입력해 주세요.")
    private String name;
    @NotBlank(message = "주소를 입력해 주세요.")
    private String address;

    public User toEntity(String encodedPassword, Address address, String qrCode) {
        return User.builder()
                .email(this.email)
                .password(encodedPassword)
                .name(this.name)
                .address(address)
                .userRole(UserRole.USER)
                .point(0)
                .qrCode(qrCode)
                .build();
    }
}
