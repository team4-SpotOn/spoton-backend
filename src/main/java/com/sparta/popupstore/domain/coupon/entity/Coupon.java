package com.sparta.popupstore.domain.coupon.entity;

import com.sparta.popupstore.domain.coupon.enums.CouponStatus;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long promotionEventId;
    private Long popupStoreId;
    private String serialNumber;
    private LocalDate couponExpirationPeriod;
    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus;
    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdAt;

    @Builder
    public Coupon(Long userId, PromotionEvent promotionEvent, String serialNumber, Long couponId, CouponStatus couponStatus) {
        this.id = couponId;
        this.userId = userId;
        this.promotionEventId = promotionEvent.getId();
        this.popupStoreId = promotionEvent.getPopupStoreId();
        this.serialNumber = serialNumber;
        this.couponStatus = couponStatus;
        this.couponExpirationPeriod = LocalDate.now().plusDays(promotionEvent.getCouponExpirationPeriod());
    }
}
