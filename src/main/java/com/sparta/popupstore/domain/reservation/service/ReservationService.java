package com.sparta.popupstore.domain.reservation.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.reservation.entity.Reservation;
import com.sparta.popupstore.domain.reservation.repository.ReservationRepository;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.entity.UserRole;
import com.sparta.popupstore.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PopupStoreRepository popupStoreRepository;
    private final UserRepository userRepository;

    @Transactional
    public Reservation createReservation(Long popupStoreId, User user, LocalDateTime reservationAt) {

        if (user.getUserRole() != UserRole.USER) {
            throw new CustomApiException(ErrorCode.NOT_USER);
        }

        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));

        if (user.getPoint() < popupStore.getPrice()) {
            throw new CustomApiException(ErrorCode.INSUFFICIENT_POINTS);
        }

        user.decreasePoint(popupStore.getPrice());

        Reservation reservation = Reservation.builder()
                .user(user)
                .popupStore(popupStore)
                .reservationAt(reservationAt)
                .build();

        return reservationRepository.save(reservation);
    }
}
