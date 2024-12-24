package com.sparta.popupstore.domain.reservation.dto.response;

import com.sparta.popupstore.domain.reservation.entity.Reservation;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationCreateResponseDto {
    private final Long id;
    private final String userName;
    private final String popupStoreName;
    private final LocalDateTime reservationAt;
    private final LocalDateTime createAt;

    public ReservationCreateResponseDto(
            Reservation reservation
    ) {
        this.id = reservation.getId();
        this.userName = reservation.getUser().getName();
        this.popupStoreName = reservation.getPopupStore().getName();
        this.reservationAt = reservation.getReservationAt();
        this.createAt = reservation.getCreatedAt();
    }
}
