package com.sparta.popupstore.domain.user.dto.response;


import com.sparta.popupstore.domain.promotionevent.entity.Coupon;
import lombok.Getter;

@Getter
public class UserMyCouponsResponseDto {
    private Long id;
    private String serial_number;
    public UserMyCouponsResponseDto(Coupon coupon) {
        this.id = coupon.getId();
        this.serial_number = coupon.getSerial_number();
    }
}
