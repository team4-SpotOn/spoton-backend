package com.sparta.popupstore.domain.oauth2.entity;

import com.sparta.popupstore.domain.common.entity.BaseEntity;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;
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
    private OAuth2Provider provider;

    private String providerId;
    private String phoneNumber;

    @Builder
    public SocialUser(Long id, OAuth2Provider provider, String providerId, String phoneNumber) {
        this.id = id;
        this.provider = provider;
        this.providerId = providerId;
        this.phoneNumber = phoneNumber;
    }
}
