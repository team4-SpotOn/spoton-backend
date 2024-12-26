package com.sparta.popupstore.domain.point.dto.response;

import com.sparta.popupstore.domain.point.entity.PointUsedLog;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class PointUseResponseDto {
    @Schema(description = "로그 아이디")
    private final Long id;
    @Schema(description = "유저이름")
    private final String name;
    @Schema(description = "충전전 소지하고있던 포인트")
    private final Integer prevPoint;
    @Schema(description = "사용한 포인트")
    private final Integer usedPoint;
    @Schema(description = "쿠폰 시리얼 번호")
    private final String couponSerialNumber;
    @Schema(description = "팝업스토어 아이디")
    private final Long popupStoreId;
    @Schema(description = "사용시간")
    protected LocalDateTime usedAt;

    public PointUseResponseDto(PointUsedLog usedLog) {
        this.id = usedLog.getId();
        this.popupStoreId = usedLog.getPopupStoreId();
        this.name = usedLog.getUser().getName();
        this.couponSerialNumber = usedLog.getCouponSerialNumber();
        this.prevPoint = usedLog.getPrevPoint();
        this.usedPoint = usedLog.getUsedPoint();
        this.usedAt = usedLog.getUsedAt();
    }
}
