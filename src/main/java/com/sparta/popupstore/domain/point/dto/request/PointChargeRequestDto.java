package com.sparta.popupstore.domain.point.dto.request;

import com.sparta.popupstore.domain.point.entity.PointChargedLog;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.Getter;

@Getter
public class PointChargeRequestDto {
  private int chargedPoint;
  private String serialNumber;

  public PointChargedLog toEntity(User user) {
    return PointChargedLog.builder()
        .user(user)
        .chargedPoint(this.chargedPoint)
        .prevPoint(user.getPoint()).serialNumber(this.serialNumber)
        .build();
  }
}
