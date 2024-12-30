package com.sparta.popupstore.domain.reservation.dto.request;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.reservation.entity.Reservation;
import com.sparta.popupstore.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ReservationCreateRequestDto {
    @NotNull(message = "예약 날짜를 입력해주세요.")
    private LocalDate reservationDate;
    @NotNull(message = "예약 시간을 입력해주세요.")
    private LocalTime reservationTime;
    @NotNull(message = "예약 인원 수를 입력해주세요.")
    @Positive(message = "예약 인원 수는 자연수 여야 합니다.")
    private Integer number;
    private String couponSerialNumber;

    public Reservation toEntity(User user, PopupStore popupStore) {
        return Reservation.builder()
                .user(user)
                .popupStore(popupStore)
                .reservationDate(this.reservationDate)
                .reservationTime(this.reservationTime)
                .number(this.number)
                .build();
    }
}
