package com.sparta.popupstore.domain.reservation.entity;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reservations")
@EntityListeners(AuditingEntityListener.class)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "popupstroe_id")
    private PopupStore popupStore;

    private LocalDate reservationDate;
    private LocalTime reservationTime;
    private Integer number;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Reservation(
            Long id,
            User user,
            PopupStore popupStore,
            LocalDate reservationDate,
            LocalTime reservationTime,
            Integer number
    ) {
        this.id = id;
        this.user = user;
        this.popupStore = popupStore;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.number = number;
    }
}
