package com.sparta.popupstore.domain.point.dto.request;

import com.sparta.popupstore.domain.point.entity.PointChargedLog;
import com.sparta.popupstore.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class PointChargeRequestDto {
    @NotNull(message = "충전할 포인트를 입력해주세요.")
    @Positive(message = "0 이상의 값을 입력해주세요.")
    private Integer chargedPoint;

    public PointChargedLog toEntity(User user) {
        return PointChargedLog.builder()
                .user(user)
                .chargedPoint(this.chargedPoint)
                .prevPoint(user.getPoint())
                .build();
    }
}
