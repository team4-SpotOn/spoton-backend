package com.sparta.popupstore.domain.coupon.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.coupon.dto.response.CouponCreateResponseDto;
import com.sparta.popupstore.domain.coupon.entity.Coupon;
import com.sparta.popupstore.domain.coupon.enums.CouponStatus;
import com.sparta.popupstore.domain.coupon.repository.CouponRepository;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import com.sparta.popupstore.domain.promotionevent.repository.PromotionEventRepository;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Coupon createCoupon(PromotionEvent promotionEvent, User user) {
        Coupon existingCoupon = couponRepository.findByIdWithPessimisticLock(promotionEvent.getId(), user.getId());
        if(existingCoupon != null) {
            throw new CustomApiException(ErrorCode.COUPON_DUPLICATE_ISSUANCE);
        }

        String uuid = UUID.randomUUID().toString();
        Coupon coupon = Coupon.builder()
                .userId(user.getId())
                .promotionEvent(promotionEvent)
                .discountPercentage(promotionEvent.getDiscountPercentage())
                .serialNumber(uuid)
                .couponStatus(CouponStatus.ISSUED)
                .couponExpirationPeriod(LocalDate.now().plusDays(promotionEvent.getCouponExpirationPeriod()))
                .build();
        return couponRepository.save(coupon);
    }
}
