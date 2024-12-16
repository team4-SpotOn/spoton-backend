package com.sparta.popupstore.domain.user.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.user.dto.request.UserValidReservationRequestDto;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.service.QRService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QRController {

    private final QRService qrService;

    @Operation(summary = "유저 QR 코드 출력", description = "고객이 자신의 QR 코드를 출력")
    @GetMapping("/users/mypage/qrCode")
    public ResponseEntity<byte[]> getUseQrCode(@AuthUser User user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(qrService.getUseQrCode(user));
    }

    @Operation(summary = "QR 코드로 유저 확인", description = "입력한 QR 코드로 유저가 맞는지 확인")
    @GetMapping("/popupstores/{popupStoreId}/qrCode")
    public ResponseEntity<Void> validReservation(
            @PathVariable Long popupStoreId,
            @RequestBody UserValidReservationRequestDto requestDto
    ) {
        qrService.validReservation(popupStoreId, requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
