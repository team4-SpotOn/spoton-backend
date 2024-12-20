package com.sparta.popupstore.domain.company.entity;

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
@Table(name = "companies")
public class Company extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String ceoName;
    private String name;
    private String phone;
    private String website;
    private String businessLicense;

    @Builder
    public Company(Long id, String email, String password, String ceoName, String name, String phone, String website, String businessLicense) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.ceoName = ceoName;
        this.name = name;
        this.phone = phone;
        this.website = website;
        this.businessLicense = businessLicense;
    }

    public void update(String ceoName, String phone, String website) {
        this.ceoName = ceoName;
        this.phone = phone;
        this.website = website;
    }

    public void delete(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
