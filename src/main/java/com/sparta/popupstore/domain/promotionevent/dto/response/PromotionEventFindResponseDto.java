package com.sparta.popupstore.domain.promotionevent.dto.response;

import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PromotionEventFindResponseDto {
    @Schema(description = "프로모션 이벤트 고유 번호")
    private final Long id;
    @Schema(description = "프로모션 이벤트 제목")
    private final String title;
    @Schema(description = "프로모션 이벤트 설명")
    private final String description;
    @Schema(description = "팝업스토어 객체")
    private final PromotionEventFindPopupStoreResponseDto promotionEventFindPopupStoreResponseDto;
    @Schema(description = "할인 율")
    private final int discountPercentage;
    @Schema(description = "총 쿠폰 갯수")
    private final int totalCount;
    @Schema(description = "프로모션 이벤트 시작일")
    private final LocalDateTime startTime;
    @Schema(description = "프로모션 이벤트 종료일")
    private final LocalDateTime endTime;

    public PromotionEventFindResponseDto(PromotionEvent promotionEvent) {
        this.id = promotionEvent.getId();
        this.title = promotionEvent.getTitle();
        this.description = promotionEvent.getDescription();
        this.promotionEventFindPopupStoreResponseDto = new PromotionEventFindPopupStoreResponseDto(promotionEvent.getPopupStore());
        this.discountPercentage = promotionEvent.getDiscountPercentage();
        this.totalCount = promotionEvent.getTotalCount();
        this.startTime = promotionEvent.getStartTime();
        this.endTime = promotionEvent.getEndTime();
    }
}
