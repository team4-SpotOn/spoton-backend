package com.sparta.popupstore.domain.promotionevent.entity;

import com.sparta.popupstore.domain.common.entity.BaseEntity;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "events")
public class PromotionEvent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "popupstore_id")
    private PopupStore popupStore;
    private String title;
    private String description;
    private Integer discountPercentage;
    private Integer couponGetCount;
    private Integer totalCount;
    private Integer couponExpirationPeriod;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String imageUrl;

    @Builder
    public PromotionEvent(
            Long id,
            PopupStore popupStore,
            String title,
            String description,
            Integer discountPercentage,
            Integer couponGetCount,
            Integer totalCount,
            Integer couponExpirationPeriod,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            String imageUrl
    ) {
        this.id = id;
        this.popupStore = popupStore;
        this.title = title;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.couponGetCount = couponGetCount;
        this.totalCount = totalCount;
        this.couponExpirationPeriod = couponExpirationPeriod;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.imageUrl = imageUrl;
    }

    public void addPopupStore(PopupStore popupStore){
        this.popupStore = popupStore;
    }

    public void updatePromotionEvent(
            String title,
            String description,
            Integer discountPercentage,
            Integer totalCount,
            Integer couponExpirationPeriod,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            String imageUrl
    ) {
        this.title = title;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.totalCount = totalCount;
        this.couponExpirationPeriod = couponExpirationPeriod;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.imageUrl = imageUrl;
    }

    public void couponGetCountUp() {
        this.couponGetCount++;
    }
}
