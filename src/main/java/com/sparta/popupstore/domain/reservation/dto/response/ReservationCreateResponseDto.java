package com.sparta.popupstore.domain.reservation.dto.response;

import com.sparta.popupstore.domain.reservation.entity.Reservation;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ReservationCreateResponseDto {
    private final Long id;
    private final String userName;
    private final String popupStoreName;
    private final LocalDate reservationDate;
    private final LocalTime reservationTime;
    private final Integer number;
    private final LocalDateTime createAt;

    public ReservationCreateResponseDto(
            Reservation reservation
    ) {
        this.id = reservation.getId();
        this.userName = reservation.getUser().getName();
        this.popupStoreName = reservation.getPopupStore().getName();
        this.reservationDate = reservation.getReservationDate();
        this.reservationTime = reservation.getReservationTime();
        this.number = reservation.getNumber();
        this.createAt = reservation.getCreatedAt();
    }
}
