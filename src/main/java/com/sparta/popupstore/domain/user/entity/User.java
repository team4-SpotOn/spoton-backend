package com.sparta.popupstore.domain.user.entity;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private Address address;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private int point;
    private String qrCode;

    @Builder
    public User(Long id, String email, String password, String name, Address address, UserRole userRole, int point, String qrCode) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.userRole = userRole;
        this.point = point;
        this.qrCode = qrCode;
    }

    public void update(Address address) {
         this.address = address;
    }

    public void delete(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void ChargePoint(int chargedPoint) {
        this.point += chargedPoint;
    }

    public void decreasePoint(int amount) {
        this.point -= amount;
    }
}
