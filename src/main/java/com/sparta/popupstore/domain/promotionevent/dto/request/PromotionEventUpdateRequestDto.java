package com.sparta.popupstore.domain.promotionevent.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PromotionEventUpdateRequestDto {
    @NotBlank(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "이벤트 설명을 입력해주셔야 합니다.")
    private String description;
    @Positive(message = "1 이상의 수만 입력해주세요.")
    @Max(value = 100, message = "할인율은 100을 초과할 수 없습니다.")
    private int discountPercentage;
    @Positive(message = "1 이상의 수만 입력해주세요.")
    private int totalCount;
    @NotNull(message = "이벤트 시작일은 공백일 수 없습니다.")
    private LocalDateTime startDateTime;
    @NotNull(message = "이벤트 종료일은 공백일 수 없습니다.")
    @Future(message = "현재 날짜보다 이후로 설정하셔야합니다.")
    private LocalDateTime endDateTime;
}
