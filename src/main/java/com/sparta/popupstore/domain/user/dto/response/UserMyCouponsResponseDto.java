package com.sparta.popupstore.domain.user.dto.response;


import com.sparta.popupstore.domain.promotionevent.entity.Coupon;
import lombok.Getter;

@Getter
public class UserMyCouponsResponseDto {
    private final Long id;
    private final String serialNumber;
    public UserMyCouponsResponseDto(Coupon coupon) {
        this.id = coupon.getId();
        this.serialNumber = coupon.getSerialNumber();
    }
}
