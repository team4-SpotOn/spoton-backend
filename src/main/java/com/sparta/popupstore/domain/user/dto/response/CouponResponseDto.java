package com.sparta.popupstore.domain.user.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class CouponResponseDto {
    private List<UserMyCouponsResponseDto> coupons;
    public CouponResponseDto(List<UserMyCouponsResponseDto> coupons) {
        this.coupons = coupons;
    }
}
