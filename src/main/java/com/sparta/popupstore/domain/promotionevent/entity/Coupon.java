package com.sparta.popupstore.domain.promotionevent.entity;

import com.sparta.popupstore.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private PromotionEvent promotionEvent;

    private String serialNumber;

    private LocalDateTime couponExpirationPeriod;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdAt;
    @Column
    private LocalDateTime deletedAt;

    @Builder
    public Coupon(User user, PromotionEvent promotionEvent, String serialNumber, Long couponId) {
        this.id = couponId;
        this.user = user;
        this.promotionEvent = promotionEvent;
        this.serialNumber = serialNumber;
        this.couponExpirationPeriod = LocalDateTime.now().plusDays(promotionEvent.getCouponExpirationPeriod());
    }
}
