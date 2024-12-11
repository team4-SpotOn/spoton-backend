package com.sparta.popupstore.domain.point.dto.response;

import com.sparta.popupstore.domain.point.entity.PointChargedLog;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PointChargedLogResponseDto {
  private final Long id;
  private final String name;
  private final int prevPoint;
  private final int chargedPoint;
  protected LocalDateTime chargedAt;

  public PointChargedLogResponseDto(PointChargedLog ChargedLog) {
    this.id = ChargedLog.getId();
    this.name = ChargedLog.getUser().getName();
    this.prevPoint = ChargedLog.getPrevPoint();
    this.chargedPoint = ChargedLog.getChargedPoint();
    this.chargedAt = ChargedLog.getChargedAt();
  }
}
