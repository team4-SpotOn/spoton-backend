package com.sparta.popupstore.domain.oauth2.service;

import com.sparta.popupstore.domain.oauth2.client.common.OAuth2UserInfoDto;
import com.sparta.popupstore.domain.oauth2.entity.SocialUser;
import com.sparta.popupstore.domain.oauth2.repository.SocialUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialUserService {

    private final SocialUserRepository socialUserRepository;

    public SocialUser signupIfAbsent(OAuth2UserInfoDto userInfo) {
        return socialUserRepository.findByPlatformAndPlatformId(userInfo.getPlatform(), userInfo.getPlatformId())
                .orElseGet(() -> socialUserRepository.save(
                        SocialUser.builder()
                                .platform(userInfo.getPlatform())
                                .platformId(userInfo.getPlatformId())
                                .build()
                ));
    }

    public void addUserAndPhone(SocialUser socialUser, Long id, String phone) {
        socialUser.addUserAndPhone(id, phone);
        socialUserRepository.save(socialUser);
    }
}
