package com.sparta.popupstore.domain.promotionevent.dto.response;

import com.sparta.popupstore.domain.promotionevent.entity.Coupon;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CouponCreateResponseDto {
    private final Long id;
    private final String serialNumber;
    private final LocalDate couponExpirationPeriod;

    public CouponCreateResponseDto(Coupon coupon) {
        this.id = coupon.getId();
        this.serialNumber = coupon.getSerialNumber();
        this.couponExpirationPeriod = coupon.getCouponExpirationPeriod();
    }
}
