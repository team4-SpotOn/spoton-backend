package com.sparta.popupstore.domain.reservation.repository;

import com.sparta.popupstore.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
