package com.sparta.popupstore.domain.admin.service;

import com.sparta.popupstore.config.PasswordEncoder;
import com.sparta.popupstore.domain.admin.dto.request.AdminSigninRequestDto;
import com.sparta.popupstore.domain.admin.entity.Admin;
import com.sparta.popupstore.domain.admin.repository.AdminRepository;
import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public Admin signin(AdminSigninRequestDto requestDto) {
        Admin admin = adminRepository.findBySigninId(requestDto.getSigninId())
                .orElseThrow(() -> new CustomApiException(ErrorCode.INCORRECT_EMAIL_OR_PASSWORD));
        if(!passwordEncoder.matches(requestDto.getPassword(), admin.getPassword())) {
            throw new CustomApiException(ErrorCode.INCORRECT_EMAIL_OR_PASSWORD);
        }

        return admin;
    }
}
