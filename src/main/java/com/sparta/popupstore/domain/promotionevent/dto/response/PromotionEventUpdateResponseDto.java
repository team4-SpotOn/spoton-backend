package com.sparta.popupstore.domain.promotionevent.dto.response;

import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PromotionEventUpdateResponseDto {
    @Schema(description = "수정 요청된 프로모션 이벤트 고유 번호")
    private final Long id;
    @Schema(description = "수정된 프로모션 이벤트 제목")
    private final String title;
    @Schema(description = "수정된 프로모션 이벤트 설명")
    private final String description;
    @Schema(description = "수정된 할인 율")
    private final int discountPercentage;
    @Schema(description = "수정된 총 쿠폰 갯수")
    private final int totalCount;
    @Schema(description = "수정된 대상 팝업스토어")
    private final Long popupStoreId;
    @Schema(description = "수정된 프로모션 이벤트 시작일")
    private final LocalDateTime startTime;
    @Schema(description = "수정된 프로모션 이벤트 종료일")
    private final LocalDateTime endTime;

    public PromotionEventUpdateResponseDto(PromotionEvent promotionEvent, Long popupStoreId) {
        this.id = promotionEvent.getId();
        this.title = promotionEvent.getTitle();
        this.description = promotionEvent.getDescription();
        this.discountPercentage = promotionEvent.getDiscountPercentage();
        this.totalCount = promotionEvent.getTotalCount();
        this.popupStoreId = popupStoreId;
        this.startTime = promotionEvent.getStartDateTime();
        this.endTime = promotionEvent.getEndDateTime();
    }
}
