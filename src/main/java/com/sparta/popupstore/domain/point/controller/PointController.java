package com.sparta.popupstore.domain.point.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.point.dto.request.PointChargeRequestDto;
import com.sparta.popupstore.domain.point.dto.request.PointUseRequestDto;
import com.sparta.popupstore.domain.point.dto.response.PointChargeResponseDto;
import com.sparta.popupstore.domain.point.dto.response.PointChargedLogResponseDto;
import com.sparta.popupstore.domain.point.dto.response.PointUseResponseDto;
import com.sparta.popupstore.domain.point.dto.response.PointUsedLogResponseDto;
import com.sparta.popupstore.domain.point.service.PointService;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/point")
@RequiredArgsConstructor
@Tag(name = "Point", description = "포인트 관련 API")
public class PointController {
  private final PointService pointService;
  private final PopupStoreRepository popupStoreRepository;

  @Operation(summary = "포인트 충전")
  @Parameter(name = "chargedPoint", description = "포인트충전량")
  @PostMapping("/charge")
  public ResponseEntity<PointChargeResponseDto> pointCharge(@AuthUser User user,@RequestBody PointChargeRequestDto chargeRequest) {
    return ResponseEntity.status(HttpStatus.CREATED).body(pointService.pointCharge(user, chargeRequest));
  }

  @Operation(summary = "포인트 충전내역 조회")
  @GetMapping("/charge")
  public ResponseEntity<List<PointChargedLogResponseDto>> pointChargeLogs(@AuthUser User user) {
    return ResponseEntity.ok(pointService.pointChargeLogs(user));
  }

  @Operation(summary = "포인트 사용")
  @Parameter(name = "usedPoint", description = "포인트사용량")
  @PostMapping("/used/popupStoreId/{popupStoreId}")
  public ResponseEntity <PointUseResponseDto> pointUsed(@AuthUser User user,@PathVariable Long popupStoreId
      ,@RequestBody PointUseRequestDto usedRequest) {
    return ResponseEntity.status(HttpStatus.CREATED).body(pointService.pointUsed(user, usedRequest, popupStoreId));
  }

  @Operation(summary = "포인트 사용내역 조회")
  @GetMapping("/used")
  public ResponseEntity<List<PointUsedLogResponseDto>> pointUsedLogs(@AuthUser User user) {
    return ResponseEntity.ok(pointService.pointUsedLogs(user));
  }
}
