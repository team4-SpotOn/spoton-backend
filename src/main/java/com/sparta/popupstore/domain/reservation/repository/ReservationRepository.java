package com.sparta.popupstore.domain.reservation.repository;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.reservation.entity.Reservation;
import com.sparta.popupstore.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByUserAndPopupStore(User user, PopupStore popupStore);

    Optional<Reservation> findByPopupStoreAndUser(PopupStore popupStore, User user);

    List<Reservation> findAllByPopupStoreAndReservationAtBetween(PopupStore popupStore, LocalDateTime startTime, LocalDateTime endTime);
}
