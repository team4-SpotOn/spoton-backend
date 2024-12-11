package com.sparta.popupstore.domain.point.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PointUsedLogResponseDto {
  private final Long id;
  private final String name;
  private final int prevPoint;
  private final int usedPoint;
  protected LocalDateTime usedAt;

  public PointUsedLogResponseDto(PointUsedLogResponseDto pointUsedLogResponseDto) {
    this.id = pointUsedLogResponseDto.getId();
    this.name = pointUsedLogResponseDto.getName();
    this.prevPoint = pointUsedLogResponseDto.getPrevPoint();
    this.usedPoint = pointUsedLogResponseDto.getUsedPoint();
    this.usedAt = pointUsedLogResponseDto.getUsedAt();
  }
}
