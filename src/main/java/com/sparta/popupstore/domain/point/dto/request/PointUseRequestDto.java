package com.sparta.popupstore.domain.point.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class PointUseRequestDto {
    @NotNull(message = "사용할 포인트를 입력해주세요.")
    @Positive(message = "0 이상의 값을 입력해주세요.")
    private Integer usedPoint;
    private String serialNumber;
}
