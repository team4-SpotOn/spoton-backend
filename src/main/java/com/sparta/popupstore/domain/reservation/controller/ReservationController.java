package com.sparta.popupstore.domain.reservation.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.reservation.entity.Reservation;
import com.sparta.popupstore.domain.reservation.service.ReservationService;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/popupstores")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/{popupStoreId}/reservation")
    public ResponseEntity<Reservation> reservePopupStore(
            @PathVariable Long popupStoreId,
            @AuthUser User user,
            @RequestParam LocalDateTime reservationAt) {
        return ResponseEntity.ok(reservationService.createReservation(popupStoreId, user, reservationAt));
    }
}
