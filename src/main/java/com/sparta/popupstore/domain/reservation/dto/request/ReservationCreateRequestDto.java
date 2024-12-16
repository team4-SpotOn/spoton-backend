package com.sparta.popupstore.domain.reservation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ReservationCreateRequestDto {
    @NotNull(message = "팝업스토어 예약일을 입력해주세요.")
    private LocalDateTime reservationAt;
}
