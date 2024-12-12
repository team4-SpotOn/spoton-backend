package com.sparta.popupstore.domain.point.dto.response;

import com.sparta.popupstore.domain.point.entity.PointChargedLog;

import com.sparta.popupstore.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PointChargedLogResponseDto {
  @Schema(description = "로그 아이디")
  private final Long id;
  @Schema(description = "유저이름")
  private final String name;
  @Schema(description = "충전전 소지하고있던 포인트")
  private final int prevPoint;
  @Schema(description = "충전 포인트")
  private final int chargedPoint;
  @Schema(description = "시리얼 넘버")
  private final String serialNumber;
  @Schema(description = "충전 시간")
  protected LocalDateTime chargedAt;

  public PointChargedLogResponseDto(User user,PointChargedLog ChargedLog) {
    this.id = ChargedLog.getId();
    this.name = user.getName();
    this.prevPoint = ChargedLog.getPrevPoint();
    this.chargedPoint = ChargedLog.getChargedPoint();
    this.serialNumber = ChargedLog.getSerialNumber();
    this.chargedAt = ChargedLog.getChargedAt();
  }
}
