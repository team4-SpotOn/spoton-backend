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
    private int point;
    private String qrCode;
    private String phone;

    @Builder
    public User(
            Long id,
            String email,
            String password,
            String name,
            Address address,
            int point,
            String qrCode,
            String phone
    ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.point = point;
        this.qrCode = qrCode;
        this.phone = phone;
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
