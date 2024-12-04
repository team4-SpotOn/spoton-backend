package com.sparta.popupstore.domain.user.entity;

import com.sparta.popupstore.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private UserRole userRole = UserRole.USER;

    @Builder
    public User(Long id, String email, String password, String name, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.userRole = userRole;
    }
}
