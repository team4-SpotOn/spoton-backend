package com.sparta.popupstore.domain.promotionevent.service;

import com.sparta.popupstore.domain.promotionevent.entity.Coupon;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import com.sparta.popupstore.domain.promotionevent.repository.CouponRepository;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public Coupon createCoupon(PromotionEvent promotionEvent, User user) {
        if (couponRepository.existsByPromotionEventIdAndUserId(promotionEvent.getId(), user.getId())) {
            throw new IllegalArgumentException("이미 발급 받으신 쿠폰입니다.");
        }
        String uuid = UUID.randomUUID().toString();
        Coupon coupon = Coupon.builder()
                .user(user)
                .promotionEvent(promotionEvent)
                .serialNumber(uuid)
                .build();
        return couponRepository.save(coupon);
    }
}
