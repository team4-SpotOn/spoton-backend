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
    private UserRole userRole = UserRole.USER;
    private int point = 0;

    @Builder
    public User(Long id, String email, String password, String name, String address, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = new Address(address);
        this.userRole = userRole;
    }

    public void update(String address) {
        // this.address = address update
    }

    public void delete(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
