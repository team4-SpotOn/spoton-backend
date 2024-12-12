package com.sparta.popupstore.domain.point.dto.response;

import com.sparta.popupstore.domain.point.entity.PointUsedLog;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PointUseResponseDto {
  @Schema(description = "로그 아이디")
  private final Long id;
  @Schema(description = "유저이름")
  private final String name;
  @Schema(description = "충전전 소지하고있던 포인트")
  private final int prevPoint;
  @Schema(description = "사용한 포인트")
  private final int usedPoint;
  @Schema(description = "시리얼 넘버")
  private final String serialNumber;
  @Schema(description = "팝업스토어 아이디")
  private final Long popupstoreId;
  @Schema(description = "사용시간")
  protected LocalDateTime usedAt;

  public PointUseResponseDto(PointUsedLog usedLog) {
    this.id = usedLog.getId();
    this.popupstoreId = usedLog.getPopupstoreId();
    this.name = usedLog.getUser().getName();
    this.serialNumber = usedLog.getSerialNumber();
    this.prevPoint = usedLog.getPrevPoint();
    this.usedPoint = usedLog.getUsedPoint();
    this.usedAt = usedLog.getUsedAt();
  }
}
