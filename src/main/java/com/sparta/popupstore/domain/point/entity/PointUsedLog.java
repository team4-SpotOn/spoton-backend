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
@Table(name = "point_used_log")
@EntityListeners(AuditingEntityListener.class)
public class PointUsedLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long popupStoreId;
    private Integer prevPoint;
    private Integer usedPoint;
    private String couponSerialNumber;

    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime usedAt;

    @Builder
    public PointUsedLog(Long id, User user, Long popupStoreId, Integer prevPoint, Integer usedPoint, String couponSerialNumber) {
        this.id = id;
        this.user = user;
        this.popupStoreId = popupStoreId;
        this.prevPoint = prevPoint;
        this.usedPoint = usedPoint;
        this.couponSerialNumber = couponSerialNumber;
    }
}
