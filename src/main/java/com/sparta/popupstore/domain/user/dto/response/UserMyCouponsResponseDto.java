package com.sparta.popupstore.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserMyCouponsResponseDto {
    private Long id;
//    private String serial_number;
    public UserMyCouponsResponseDto(Long id) {
        this.id = id;
//        this.serial_number = serial_number;
    }
}
