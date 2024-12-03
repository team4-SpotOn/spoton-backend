package com.sparta.popupstore.domain.promotionalevent.dto.request;

import com.sparta.popupstore.domain.promotionalevent.entity.PromotionalEvent;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PromotionalEventCreateRequestDto {
    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "이벤트 설명을 입력해주셔야 합니다.")
    private String description;
    @Positive(message = "1 이상의 수만 입력해주세요.")
    private int discountPercentage;
    @Positive(message = "1 이상의 수만 입력해주세요.")
    private int totalCount;
    @NotNull(message = "이벤트 시작일은 공백일 수 없습니다.")
    private LocalDateTime startTime;
    @NotNull(message = "이벤트 종료일은 공백일 수 없습니다.")
    @Future(message = "현재 날짜보다 이후로 설정하셔야합니다.")
    private LocalDateTime endTime;

    public PromotionalEvent toEvent() {
        return PromotionalEvent.builder()
                .title(this.title)
                .description(this.description)
                .discountPercentage(this.discountPercentage)
                .totalCount(this.totalCount)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .build();
    }
}
