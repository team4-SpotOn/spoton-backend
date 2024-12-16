package com.sparta.popupstore.domain.reservation.dto.response;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.reservation.entity.Reservation;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationCreateResponseDto {
    private final String userName;
    private final String popupStoreName;
    private final LocalDateTime reservationAt;
    private final LocalDateTime createAt;

    public ReservationCreateResponseDto(
            Reservation reservation
    ) {
        this.userName = reservation.getUser().getName();
        this.popupStoreName = reservation.getPopupStore().getName();
        this.reservationAt = reservation.getReservationAt();
        this.createAt = reservation.getCreatedAt();
    }
}
