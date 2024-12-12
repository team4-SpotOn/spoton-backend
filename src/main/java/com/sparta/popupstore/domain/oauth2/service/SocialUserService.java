package com.sparta.popupstore.domain.oauth2.service;

import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfo;
import com.sparta.popupstore.domain.oauth2.entity.SocialUser;
import com.sparta.popupstore.domain.oauth2.repository.SocialUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialUserService {

    private final SocialUserRepository socialUserRepository;

    public SocialUser signupIfAbsent(OAuth2UserInfo userInfo) {
        return socialUserRepository.findByProviderAndProviderId(userInfo.getProvider(), userInfo.getProviderId())
                .orElseGet(() -> {
                    SocialUser newSocialUser = SocialUser.builder()
                            .provider(userInfo.getProvider())
                            .providerId(userInfo.getProviderId())
                            .email(userInfo.getEmail())
                            .build();
                    return socialUserRepository.save(newSocialUser);
                });
    }
}
