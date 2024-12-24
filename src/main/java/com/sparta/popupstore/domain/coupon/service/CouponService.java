package com.sparta.popupstore.domain.coupon.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.coupon.entity.Coupon;
import com.sparta.popupstore.domain.coupon.enums.CouponStatus;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import com.sparta.popupstore.domain.coupon.repository.CouponRepository;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public Coupon createCoupon(PromotionEvent promotionEvent, User user) {
        Coupon existingCoupon = couponRepository.findByIdWithPessimisticLock(promotionEvent.getId(), user.getId());
        if (existingCoupon != null) {
            throw new CustomApiException(ErrorCode.COUPON_DUPLICATE_ISSUANCE);
        }

        String uuid = UUID.randomUUID().toString();
        Coupon coupon = Coupon.builder()
                .userId(user.getId())
                .promotionEvent(promotionEvent)
                .serialNumber(uuid)
                .couponStatus(CouponStatus.ISSUED)
                .build();
        return couponRepository.save(coupon);
    }
}
