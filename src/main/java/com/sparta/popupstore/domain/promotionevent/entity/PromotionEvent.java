package com.sparta.popupstore.domain.promotionevent.entity;

import com.sparta.popupstore.domain.common.entity.BaseEntity;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    private int discountPercentage;
    @ColumnDefault("0")
    private int couponGetCount;
    private int totalCount;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Builder
    public PromotionEvent(
            Long id,
            PopupStore popupStore,
            String title,
            String description,
            int discountPercentage,
            int couponGetCount,
            int totalCount,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    ) {
        this.id = id;
        this.popupStore = popupStore;
        this.title = title;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.couponGetCount = couponGetCount;
        this.totalCount = totalCount;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public void addPopupStore(PopupStore popupStore){
        this.popupStore = popupStore;
    }

    public void updatePromotionEvent(
            String title,
            String description,
            int discountPercentage,
            int totalCount,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime
    ) {
        this.title = title;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.totalCount = totalCount;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public void couponGetCountUp() {
        this.couponGetCount++;
    }
}
