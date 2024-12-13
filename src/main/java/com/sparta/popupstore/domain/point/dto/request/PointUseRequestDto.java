package com.sparta.popupstore.domain.point.dto.request;

import com.sparta.popupstore.domain.point.entity.PointUsedLog;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.Getter;

@Getter
public class PointUseRequestDto {
  private int usedPoint;

  public PointUsedLog toEntity(User user, PopupStore popupStore) {
    return PointUsedLog.builder()
        .user(user)
        .usedPoint(this.usedPoint)
        .prevPoint(user.getPoint())
        .popupstoreId(popupStore.getId())
        .build();
  }
}
