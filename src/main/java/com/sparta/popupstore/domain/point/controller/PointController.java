package com.sparta.popupstore.domain.point.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.point.entity.PointChargedLog;
import com.sparta.popupstore.domain.point.entity.PointUsedLog;
import com.sparta.popupstore.domain.point.service.PointService;
import com.sparta.popupstore.domain.user.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/point")
@RequiredArgsConstructor
@Tag(name = "Point", description = "포인트 관련 API")
public class PointController {
  private final PointService pointService;

  @PostMapping("/charge")
  public PointChargedLog pointCharge(@AuthUser User user,@RequestBody PointChargedLog pointChargedLog) {
    return null;
  }

  @GetMapping("/charge")
  public PointChargedLog pointChargeLoge(@AuthUser User user,@RequestBody PointChargedLog pointChargedLog) {
    return null;
  }

  @GetMapping("/use")
  public PointUsedLog pointUsedLog(@AuthUser User user,@RequestBody PointUsedLog pointUsedLog) {
    return null;
  }
}
