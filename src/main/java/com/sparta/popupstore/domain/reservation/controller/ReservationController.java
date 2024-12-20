package com.sparta.popupstore.domain.reservation.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.reservation.dto.request.ReservationCreateRequestDto;
import com.sparta.popupstore.domain.reservation.entity.Reservation;
import com.sparta.popupstore.domain.reservation.service.ReservationService;
import com.sparta.popupstore.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "예약 API", description = "예약과 예약 취소 API")
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "팝업스토어 예약")
    @Parameter(name = "popupStoreId", description = "예약할 팝업스토어의 기본키")
    @Parameter(name = "user", description = "로그인한 유저")
    @Parameter(name = "reservationAt", description = "예약할 시각")
    @PostMapping("/popupstores/{popupStoreId}/reservation")
    public ResponseEntity<Reservation> reservePopupStore(
            @PathVariable Long popupStoreId,
            @AuthUser User user,
            @RequestBody ReservationCreateRequestDto requestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservationService.createReservation(popupStoreId, user, requestDto));
    }

    @Operation(summary = "팝업스토어 예약 취소")
    @Parameter(name = "reservationId", description = "삭제할 예약의 기본키")
    @Parameter(name = "user", description = "로그인한 유저")
    @DeleteMapping("/popupstores/reservation/{reservationId}")
    public ResponseEntity<Void> cancelReservation(
            @PathVariable Long reservationId,
            @AuthUser User user
    ) {
        reservationService.cancelReservation(reservationId, user);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
