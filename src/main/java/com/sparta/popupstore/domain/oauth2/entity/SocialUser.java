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

    private String platformId;
    private String phoneNumber;

    @Builder
    public SocialUser(Long id, OAuth2Platform platform, String platformId, String phoneNumber) {
        this.id = id;
        this.platform = platform;
        this.platformId = platformId;
        this.phoneNumber = phoneNumber;
    }
}
