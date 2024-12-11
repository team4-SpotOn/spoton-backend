package com.sparta.popupstore.domain.point.dto.response;

import com.sparta.popupstore.domain.point.entity.PointChargedLog;
import com.sparta.popupstore.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PointChargeResponseDto {
  private final Long id;
  private final String name;
  private final int prevPoint;
  private final int chargedPoint;
  private final LocalDateTime chargedAt;

  public PointChargeResponseDto(PointChargedLog chargedLog) {
    this.id = chargedLog.getId();
    this.name = chargedLog.getUser().getName();
    this.prevPoint = chargedLog.getPrevPoint();
    this.chargedPoint = chargedLog.getChargedPoint();
    this.chargedAt = chargedLog.getChargedAt();
  }
}
