package com.sparta.popupstore.domain.point.dto.response;

import com.sparta.popupstore.domain.user.entity.User;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PointChargedLogResponseDto {
  private final Long id;
  private final User user;
  private final int prevPoint;
  private final int chargedPoint;
  protected LocalDateTime chargedAt;

  public PointChargedLogResponseDto(Long id ,User user, int prevPoint, int chargedPoint, LocalDateTime chargedAt) {
    this.id = id;
    this.user = user;
    this.prevPoint = prevPoint;
    this.chargedPoint = chargedPoint;
    this.chargedAt = chargedAt;
  }
}
