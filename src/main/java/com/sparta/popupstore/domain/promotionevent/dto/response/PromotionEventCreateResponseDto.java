package com.sparta.popupstore.domain.promotionevent.dto.response;

import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PromotionEventCreateResponseDto {
    @Schema(description = "프로모션 이벤트 고유 번호")
    private final Long id;
    @Schema(description = "팝업스토어 고유번호")
    private final Long popupStoreId;
    @Schema(description = "프로모션 이벤트 제목")
    private final String title;
    @Schema(description = "프로모션 이벤트 설명")
    private final String description;
    @Schema(description = "할인 율")
    private final Integer discountPercentage;
    @Schema(description = "총 쿠폰 갯수")
    private final Integer totalCount;
    @Schema(description = "쿠폰 만료 기간")
    private final Integer couponExpirationPeriod;
    @Schema(description = "프로모션 이벤트 시작일")
    private final LocalDateTime startDateTime;
    @Schema(description = "프로모션 이벤트 종료일")
    private final LocalDateTime endDateTime;
    @Schema(description = "이미지 경로")
    private final String imageUrl;

    public PromotionEventCreateResponseDto(PromotionEvent promotionEvent) {
        this.id = promotionEvent.getId();
        this.popupStoreId = promotionEvent.getPopupStore() != null ? promotionEvent.getPopupStore().getId() : null;
        this.title = promotionEvent.getTitle();
        this.description = promotionEvent.getDescription();
        this.discountPercentage = promotionEvent.getDiscountPercentage();
        this.totalCount = promotionEvent.getTotalCount();
        this.couponExpirationPeriod = promotionEvent.getCouponExpirationPeriod();
        this.startDateTime = promotionEvent.getStartDateTime();
        this.endDateTime = promotionEvent.getEndDateTime();
        this.imageUrl = promotionEvent.getImageUrl();
    }
}
