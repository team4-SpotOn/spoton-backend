package com.sparta.popupstore.domain.point.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.point.dto.request.PointChargeRequestDto;
import com.sparta.popupstore.domain.point.dto.response.PointChargeResponseDto;
import com.sparta.popupstore.domain.point.dto.response.PointChargedLogResponseDto;
import com.sparta.popupstore.domain.point.dto.response.PointUsedLogResponseDto;
import com.sparta.popupstore.domain.point.service.PointService;
import com.sparta.popupstore.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "포인트 API", description = "포인트 충전 및 사용과 내역 조회 API")
@RequestMapping("/users/point")
public class PointController {
    private final PointService pointService;

    @Operation(summary = "포인트 충전")
    @Parameter(name = "chargedPoint", description = "포인트충전량")
    @Parameter(name = "serialNumber", description = "쿠폰 일련 번호")
    @PostMapping("/charge")
    public ResponseEntity<PointChargeResponseDto> pointCharge(
            @AuthUser User user,
            @RequestBody PointChargeRequestDto chargeRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pointService.pointCharge(user, chargeRequest));
    }

    @Operation(summary = "포인트 충전내역 조회")
    @GetMapping("/charge")
    public ResponseEntity<List<PointChargedLogResponseDto>> pointChargeLogs(@AuthUser User user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pointService.pointChargeLogs(user));
    }

//    @Operation(summary = "포인트 사용")
//    @Parameter(name = "usedPoint", description = "포인트사용량")
//    @Parameter(name = "serialNumber", description = "쿠폰 일련 번호")
//    @PostMapping("/used/popupStore/{popupStoreId}")
//    public ResponseEntity<PointUseResponseDto> pointUsed(
//            @AuthUser User user,
//            @PathVariable Long popupStoreId,
//            @RequestBody PointUseRequestDto usedRequest
//    ) {
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(pointService.pointUsed(user, popupStoreId, usedRequest));
//    }

    @Operation(summary = "포인트 사용내역 조회")
    @GetMapping("/used")
    public ResponseEntity<List<PointUsedLogResponseDto>> pointUsedLogs(@AuthUser User user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pointService.pointUsedLogs(user));
    }
}
