package com.sparta.popupstore.domain.reservation.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttribute;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttributeEnum;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreAttributeRepository;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.reservation.dto.request.ReservationCreateRequestDto;
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
    private final PopupStoreAttributeRepository popupStoreAttributeRepository;

    @Transactional
    public Reservation createReservation(Long popupStoreId, User user, ReservationCreateRequestDto requestDto) {
        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));

        /*
        최대 예약 가능 인원을 초과할 경우 예약 불가하게 로직 변경 예정
            if (!popupStore.canReserve()) throw new CustomApiException(ErrorCode.POPUP_STORE_FULL);
         */

        PopupStoreAttribute popupStoreAttribute = popupStoreAttributeRepository.findAllByPopupStore(popupStore).stream()
                .filter(attribute -> attribute.getAttribute().equals(PopupStoreAttributeEnum.RESERVATION))
                .findFirst()
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_CAN_NOT_RESERVATION));

        if(!popupStoreAttribute.getIsAllow()) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_CAN_NOT_RESERVATION);
        }

        if (user.getPoint() < popupStore.getPrice()) {
            throw new CustomApiException(ErrorCode.INSUFFICIENT_POINTS);
        }

        user.decreasePoint(popupStore.getPrice());

        Reservation reservation = Reservation.builder()
                .user(user)
                .popupStore(popupStore)
                .reservationAt(requestDto.getReservationAt())
                .createdAt(LocalDateTime.now())
                .build();

        return reservationRepository.save(reservation);
    }

    @Transactional
    public void cancelReservation(Long reservationId, User user) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.RESERVATION_NOT_FOUND));

        if (!reservation.getUser().equals(user)) {
            throw new CustomApiException(ErrorCode.RESERVATION_FORBIDDEN);
        }

        if (reservation.getReservationAt().isBefore(LocalDate.now().plusDays(1).atStartOfDay())) {
            throw new CustomApiException(ErrorCode.RESERVATION_CANCELLATION_NOT_ALLOWED);
        }

        reservationRepository.delete(reservation);
    }
}
