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
import java.time.LocalTime;

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

        reservationValid(
                popupStore,
                requestDto.getReservationDate(),
                requestDto.getReservationTime(),
                requestDto.getNumber()
        );
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
        if(reservation.getReservationDate().isBefore(LocalDate.now().plusDays(1))) {
            throw new CustomApiException(ErrorCode.RESERVATION_CANCELLATION_NOT_ALLOWED);
        }

        reservationRepository.delete(reservation);
    }

    private void reservationValid(PopupStore popupStore, LocalDate reservationDate, LocalTime reservationTime, Integer number) {
        popupStoreBundleService.reservationValid(popupStore, reservationDate, reservationTime);

        int sumNumber = reservationRepository.findAllByPopupStoreAndReservationDate(
                        popupStore,
                        reservationDate
                ).stream()
                .filter(reservation -> reservation.getReservationTime().getHour() == reservationTime.getHour())
                .map(Reservation::getNumber)
                .reduce(number, Integer::sum);

        if(sumNumber > popupStore.getReservationLimit()) {
            throw new CustomApiException(ErrorCode.RESERVATION_LIMIT_OVER);
        }
    }
}
