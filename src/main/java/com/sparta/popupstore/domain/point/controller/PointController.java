package com.sparta.popupstore.domain.point.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.point.dto.request.PointChargeRequestDto;
import com.sparta.popupstore.domain.point.dto.response.PointChargeResponseDto;
import com.sparta.popupstore.domain.point.dto.response.PointChargedLogResponseDto;
import com.sparta.popupstore.domain.point.entity.PointChargedLog;
import com.sparta.popupstore.domain.point.entity.PointUsedLog;
import com.sparta.popupstore.domain.point.service.PointService;
import com.sparta.popupstore.domain.user.entity.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @PostMapping("/charge/{userId}")
  public ResponseEntity<PointChargeResponseDto> pointCharged(@AuthUser User user,@RequestBody PointChargeRequestDto chargeRequest) {
    PointChargeResponseDto result = pointService.pointCharge(user, chargeRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @GetMapping("/charge")
  public ResponseEntity<List<PointChargedLog>> pointChargeLoge(@AuthUser User user) {
    List<PointChargedLog> chargedLogs = pointService.pointChargeLogs(user);
    return ResponseEntity.ok(chargedLogs);
  }

  @GetMapping("/used")
  public ResponseEntity<List<PointUsedLog>> pointUsedLog(@AuthUser User user,@RequestBody PointUsedLog pointUsedLog) {
    List<PointUsedLog> UsedLogs = pointService.pointUsedLogs(pointUsedLog);
    return ResponseEntity.ok(UsedLogs);
  }
}
