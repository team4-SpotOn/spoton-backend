package com.sparta.popupstore.domain.reservation.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttribute;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttributeEnum;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreAttributeRepository;
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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PopupStoreRepository popupStoreRepository;
    private final PopupStoreAttributeRepository popupStoreAttributeRepository;

    @Transactional
    public Reservation createReservation(Long popupStoreId, User user, LocalDateTime reservationAt) {

        if (user.getUserRole() != UserRole.USER) {
            throw new CustomApiException(ErrorCode.NOT_USER);
        }

        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));

        List<PopupStoreAttribute> attributes = popupStoreAttributeRepository.findByPopupStore(popupStore);

        PopupStoreAttributeEnum target = PopupStoreAttributeEnum.RESERVATION;
        Optional<PopupStoreAttribute> userOptional = attributes.stream()
                .filter(attribute -> attribute.getAttribute().equals(target))
                .findFirst();

        if (userOptional.isPresent()) {
            if(!userOptional.get().getIsAllow()) throw new CustomApiException(ErrorCode.POPUP_STORE_CAN_NOT_RESERVATION);
        }

        if (user.getPoint() < popupStore.getPrice()) throw new CustomApiException(ErrorCode.INSUFFICIENT_POINTS);

        user.decreasePoint(popupStore.getPrice());

        Reservation reservation = Reservation.builder()
                .user(user)
                .popupStore(popupStore)
                .reservationAt(reservationAt)
                .build();

        return reservationRepository.save(reservation);
    }
}
