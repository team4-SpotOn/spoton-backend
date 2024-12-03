package com.sparta.popupstore.domain.promotionalevent.dto.response;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.promotionalevent.entity.PromotionalEvent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
public class PromotionEventResponseDto {
    @Schema(description = "프로모션 이벤트 고유 번호")
    private final Long id;
    @Schema(description = "프로모션 이벤트 제목")
    private final String title;
    @Schema(description = "프로모션 이벤트 설명")
    private final String description;
    @Schema(description = "팝업스토어 객체")
    private final PopupStore popupStore;
    @Schema(description = "할인 율")
    private final int discountPercentage;
    @Schema(description = "총 쿠폰 갯수")
    private final int totalCount;
    @Schema(description = "프로모션 이벤트 시작일")
    private final LocalDateTime startTime;
    @Schema(description = "프로모션 이벤트 종료일")
    private final LocalDateTime endTime;

    public PromotionEventResponseDto(PromotionalEvent promotionalEvent) {
        this.id = promotionalEvent.getId();
        this.title = promotionalEvent.getTitle();
        this.description = promotionalEvent.getDescription();
        this.popupStore = promotionalEvent.getPopupStore();
        this.discountPercentage = promotionalEvent.getDiscountPercentage();
        this.totalCount = promotionalEvent.getTotalCount();
        this.startTime = promotionalEvent.getStartTime();
        this.endTime = promotionalEvent.getEndTime();
    }
}
