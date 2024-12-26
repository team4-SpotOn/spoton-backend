package com.sparta.popupstore.domain.user.dto.response;


import com.sparta.popupstore.domain.coupon.entity.Coupon;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserMyCouponsResponseDto {
    @Schema(description = "쿠폰 고유번호")
    private final Long id;
    @Schema(description = "쿠폰 시리얼번호")
    private final String serialNumber;

    public UserMyCouponsResponseDto(Coupon coupon) {
        this.id = coupon.getId();
        this.serialNumber = coupon.getSerialNumber();
    }
}
