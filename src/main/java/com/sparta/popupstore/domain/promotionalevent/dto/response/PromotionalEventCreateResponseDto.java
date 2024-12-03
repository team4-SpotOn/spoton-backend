package com.sparta.popupstore.domain.promotionalevent.dto.response;

import com.sparta.popupstore.domain.promotionalevent.entity.PromotionalEvent;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PromotionalEventCreateResponseDto {
    private final Long id;
    private final String title;
    private final String description;
    private final int discountPercentage;
    private final int totalCount;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public PromotionalEventCreateResponseDto(PromotionalEvent  promotionalEvent) {
        this.id = promotionalEvent.getId();
        this.title = promotionalEvent.getTitle();
        this.description = promotionalEvent.getDescription();
        this.discountPercentage = promotionalEvent.getDiscountPercentage();
        this.totalCount = promotionalEvent.getTotalCount();
        this.startTime = promotionalEvent.getStartTime();
        this.endTime = promotionalEvent.getEndTime();
    }
}
