package com.sparta.popupstore.domain.point.dto.response;

import com.sparta.popupstore.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PointUsedLogResponseDto {
  private final Long id;
  private final User user;
  private final int prevPoint;
  private final int usedPoint;
  protected LocalDateTime usedAt;

  public PointUsedLogResponseDto(Long id, User user, int prevPoint, int usedPoint, LocalDateTime usedAt) {
    this.id = id;
    this.user = user;
    this.prevPoint = prevPoint;
    this.usedPoint = usedPoint;
    this.usedAt = usedAt;
  }
}
