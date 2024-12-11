package com.sparta.popupstore.domain.point.dto.response;

import com.sparta.popupstore.domain.user.entity.User;
import lombok.Getter;

@Getter
public class PointChargeResponseDto {
  private final User user;
  private final int prevPoint;
  private final int chargedPoint;

  public PointChargeResponseDto(User user, int prevPoint, int chargedPoint) {
    this.user = user;
    this.prevPoint = prevPoint;
    this.chargedPoint = chargedPoint;
  }
}
