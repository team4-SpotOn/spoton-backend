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
        return socialUserRepository.findByPlatformAndPlatformId(userInfo.getPlatform(), userInfo.getPlatformId())
                .orElseGet(() -> socialUserRepository.save(
                        SocialUser.builder()
                                .platform(userInfo.getPlatform())
                                .platformId(userInfo.getPlatformId())
                                .build()
                ));
    }
}
