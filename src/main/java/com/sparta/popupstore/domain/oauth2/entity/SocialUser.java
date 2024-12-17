package com.sparta.popupstore.domain.oauth2.entity;

import com.sparta.popupstore.domain.common.entity.BaseEntity;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "socialusers")
public class SocialUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OAuth2Platform platform;

    private Long userId;
    private String platformId;
    private String phone;

    @Builder
    public SocialUser(Long id, OAuth2Platform platform, Long userId, String platformId, String phone) {
        this.id = id;
        this.platform = platform;
        this.userId = userId;
        this.platformId = platformId;
        this.phone = phone;
    }

    public void addUserAndPhone(Long userId, String phone) {
        this.userId = userId;
        this.phone = phone;
    }
}
