package com.sparta.popupstore.domain.point.dto.response;

import com.sparta.popupstore.domain.point.entity.PointUsedLog;
import com.sparta.popupstore.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PointUseResponseDto {
  private final String name;
  private final int prevPoint;
  private final int usedPoint;

  public PointUseResponseDto(PointUsedLog usedLog) {
    this.name = usedLog.getUser().getName();
    this.prevPoint = usedLog.getPrevPoint();
    this.usedPoint = usedLog.getUsedPoint();
  }
}
