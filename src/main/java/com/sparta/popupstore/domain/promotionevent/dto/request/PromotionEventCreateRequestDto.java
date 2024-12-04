package com.sparta.popupstore.domain.promotionevent.dto.request;

import com.sparta.popupstore.domain.common.annotation.StartAndDateTimeCheck;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@StartAndDateTimeCheck(startDateTime = "startDateTime", endDateTime = "endDateTime")
public class PromotionEventCreateRequestDto {
    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "이벤트 설명을 입력해주셔야 합니다.")
    private String description;
    @Positive(message = "1 이상의 수만 입력해주세요.")
    @Max(value = 100, message = "할인율은 100을 초과할 수 없습니다.")
    private int discountPercentage;
    @Positive(message = "1 이상의 수만 입력해주세요.")
    private int totalCount;
    @Positive(message = "쿠폰 만료기간은 하루 미만일 수 없습니다.")
    @Max(value = 30, message = "쿠폰 만료기간은 30일을 초과할 수 없습니다.")
    private int couponExpirationPeriod;
    @NotNull(message = "이벤트 시작일은 공백일 수 없습니다.")
    private LocalDateTime startDateTime;
    @NotNull(message = "이벤트 종료일은 공백일 수 없습니다.")
    private LocalDateTime endDateTime;

    public PromotionEvent toEvent() {
        return PromotionEvent.builder()
                .title(this.title)
                .description(this.description)
                .discountPercentage(this.discountPercentage)
                .totalCount(this.totalCount)
                .couponExpirationPeriod(this.couponExpirationPeriod)
                .startDateTime(this.startDateTime)
                .endDateTime(this.endDateTime)
                .build();
    }
}
