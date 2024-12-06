package com.sparta.popupstore.domain.point.entity;

import com.sparta.popupstore.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "point_charged_log")
public class PointChargedLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int prevPoint;
    private int chargedPoint;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime chargedAt;
}
