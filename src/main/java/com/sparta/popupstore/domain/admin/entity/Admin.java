package com.sparta.popupstore.domain.admin.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String signinId;
    private String password;

    @Builder
    public Admin(Long id, String signinId, String password) {
        this.id = id;
        this.signinId = signinId;
        this.password = password;
    }
}
