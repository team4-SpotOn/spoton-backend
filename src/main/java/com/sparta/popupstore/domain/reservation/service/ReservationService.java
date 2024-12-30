package com.sparta.popupstore.domain.reservation.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.point.service.PointService;
import com.sparta.popupstore.domain.popupstore.bundle.service.PopupStoreBundleService;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.reservation.dto.request.ReservationCreateRequestDto;
import com.sparta.popupstore.domain.reservation.dto.response.ReservationCreateResponseDto;
import com.sparta.popupstore.domain.reservation.entity.Reservation;
import com.sparta.popupstore.domain.reservation.repository.ReservationRepository;
import com.sparta.popupstore.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PopupStoreRepository popupStoreRepository;
    private final PopupStoreBundleService popupStoreBundleService;
    private final PointService pointService;

    @Transactional
    public ReservationCreateResponseDto createReservation(User user, Long popupStoreId, ReservationCreateRequestDto requestDto) {
        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));

        reservationValid(popupStore, requestDto.getReservationAt(), requestDto.getNumber());
        pointService.pointUsed(user, popupStore, requestDto.getNumber(), requestDto.getCouponSerialNumber());

        Reservation reservation = reservationRepository.save(requestDto.toEntity(user, popupStore));
        return new ReservationCreateResponseDto(reservation);
    }

    @Transactional
    public void cancelReservation(User user, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.RESERVATION_NOT_FOUND));
        if(!reservation.getUser().equals(user)) {
            throw new CustomApiException(ErrorCode.RESERVATION_FORBIDDEN);
        }
        if(reservation.getReservationAt().toLocalDate().isBefore(LocalDate.now().plusDays(1))) {
            throw new CustomApiException(ErrorCode.RESERVATION_CANCELLATION_NOT_ALLOWED);
        }

        reservationRepository.delete(reservation);
    }

    private void reservationValid(PopupStore popupStore, LocalDateTime reservationAt, Integer number) {
        popupStoreBundleService.reservationValid(popupStore, reservationAt);

        int sumReservationNumber = reservationRepository.findAllByPopupStoreAndReservationAtBetween(
                        popupStore,
                        reservationAt.withMinute(0),
                        reservationAt.withMinute(59)
                ).stream()
                .map(Reservation::getNumber)
                .reduce(0, Integer::sum);

        if(sumReservationNumber + number > popupStore.getReservationLimit()) {
            throw new CustomApiException(ErrorCode.RESERVATION_LIMIT_OVER);
        }
    }
}
