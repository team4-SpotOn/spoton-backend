package com.sparta.popupstore.domain.coupon.dto.response;

import com.sparta.popupstore.domain.coupon.entity.Coupon;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CouponCreateResponseDto {
    @Schema(description = "쿠폰 고유 번호")
    private final Long id;
    @Schema(description = "쿠폰 시리얼 번호")
    private final String serialNumber;
    @Schema(description = "쿠폰 만료일")
    private final LocalDate couponExpirationPeriod;

    public CouponCreateResponseDto(Coupon coupon) {
        this.id = coupon.getId();
        this.serialNumber = coupon.getSerialNumber();
        this.couponExpirationPeriod = coupon.getCouponExpirationPeriod();
    }
}
