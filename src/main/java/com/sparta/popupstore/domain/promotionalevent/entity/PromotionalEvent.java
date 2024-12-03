package com.sparta.popupstore.domain.promotionalevent.entity;

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
public class PromotionalEvent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "popupstore_id")
    private PopupStore popupStore;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private int discountPercentage;
    @Column(nullable = false)
    @ColumnDefault("0")
    private int couponGetCount;
    @Column(nullable = false)
    private int totalCount;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Builder
    public PromotionalEvent(Long id, PopupStore popupStore, String title, String description, int discountPercentage, int couponGetCount, int totalCount, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.popupStore = popupStore;
        this.title = title;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.couponGetCount = couponGetCount;
        this.totalCount = totalCount;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void addPopupStore(PopupStore popupStore){
        this.popupStore = popupStore;
    }
}
