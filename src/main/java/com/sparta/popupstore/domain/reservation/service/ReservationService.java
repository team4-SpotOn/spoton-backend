package com.sparta.popupstore.domain.reservation.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreAttribute;
import com.sparta.popupstore.domain.popupstore.bundle.enums.PopupStoreAttributeEnum;
import com.sparta.popupstore.domain.popupstore.bundle.repository.PopupStoreAttributeRepository;
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

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PopupStoreRepository popupStoreRepository;
    private final PopupStoreAttributeRepository popupStoreAttributeRepository;

    @Transactional
    public ReservationCreateResponseDto createReservation(User user, Long popupStoreId, ReservationCreateRequestDto requestDto) {
        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));

        PopupStoreAttribute popupStoreAttribute = popupStoreAttributeRepository
                .findByPopupStoreAndAttribute(popupStore, PopupStoreAttributeEnum.RESERVATION)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_CAN_NOT_RESERVATION));
        if(!popupStoreAttribute.getIsAllow()) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_CAN_NOT_RESERVATION);
        }

        if(user.getPoint() < popupStore.getPrice()) {
            throw new CustomApiException(ErrorCode.INSUFFICIENT_POINTS);
        }
        user.decreasePoint(popupStore.getPrice());

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

        if(reservation.getReservationAt().isBefore(LocalDate.now().plusDays(1).atStartOfDay())) {
            throw new CustomApiException(ErrorCode.RESERVATION_CANCELLATION_NOT_ALLOWED);
        }

        reservationRepository.delete(reservation);
    }
}
