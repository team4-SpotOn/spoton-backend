package com.sparta.popupstore.domain.promotionevent.dto.response;

import com.sparta.popupstore.domain.promotionevent.entity.Coupon;
import lombok.Getter;

@Getter
public class CouponCreateResponseDto {
    private final Long id;
    private final String serialNumber;
    private final String userName;
    private final String popupStoreName;
    private final int couponExpirationPeriod;

    public CouponCreateResponseDto(Coupon coupon) {
        this.id = coupon.getId();
        this.serialNumber = coupon.getSerialNumber();
        this.userName = coupon.getUser().getName();
        this.popupStoreName = coupon.getPromotionEvent()
                .getPopupStore() != null ? coupon.getPromotionEvent().getPopupStore().getName() : null;
        this.couponExpirationPeriod = coupon.getCouponExpirationPeriod();
    }
}
