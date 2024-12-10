package com.sparta.popupstore.domain.oauth2.repository;

import com.sparta.popupstore.domain.oauth2.entity.SocialUser;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {

    Optional<SocialUser> findByProviderAndProviderId(OAuth2Provider provider, Long providerId);
}
