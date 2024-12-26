package com.sparta.popupstore.domain.point.entity;

import com.sparta.popupstore.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "point_charged_log")
@EntityListeners(AuditingEntityListener.class)
public class PointChargedLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer prevPoint;
    private Integer chargedPoint;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime chargedAt;

    @Builder
    public PointChargedLog(Long id, User user, Integer prevPoint, Integer chargedPoint, LocalDateTime chargedAt) {
        this.id = id;
        this.user = user;
        this.prevPoint = prevPoint;
        this.chargedPoint = chargedPoint;
        this.chargedAt = chargedAt;
    }
}
