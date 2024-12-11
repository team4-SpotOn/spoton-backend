package com.sparta.popupstore.domain.point.dto.response;

import com.sparta.popupstore.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PointUseResponseDto {
  private final User user;
  private final int prevPoint;
  private final int usedPoint;

  public PointUseResponseDto(User user, int prevPoint, int usedPoint) {
    this.user = user;
    this.prevPoint = prevPoint;
    this.usedPoint = usedPoint;
  }
}
