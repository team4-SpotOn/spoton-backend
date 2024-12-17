package com.sparta.popupstore.domain.user.service;

import com.sparta.popupstore.config.PasswordEncoder;
import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.kakaoaddress.service.KakaoAddressService;
import com.sparta.popupstore.domain.promotionevent.repository.CouponRepository;
import com.sparta.popupstore.domain.user.dto.request.UserDeleteRequestDto;
import com.sparta.popupstore.domain.user.dto.request.UserSigninRequestDto;
import com.sparta.popupstore.domain.user.dto.request.UserSignupRequestDto;
import com.sparta.popupstore.domain.user.dto.request.UserUpdateRequestDto;
import com.sparta.popupstore.domain.user.dto.response.UserMyCouponsResponseDto;
import com.sparta.popupstore.domain.user.dto.response.UserMyPageResponseDto;
import com.sparta.popupstore.domain.user.dto.response.UserSignupResponseDto;
import com.sparta.popupstore.domain.user.dto.response.UserUpdateResponseDto;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final KakaoAddressService kakaoAddressService;
    private final PasswordEncoder passwordEncoder;

    public UserSignupResponseDto signup(UserSignupRequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())) {
            throw new CustomApiException(ErrorCode.ALREADY_EXIST_EMAIL);
        }
        if(userRepository.existsByPhone(requestDto.getPhone())) {
            throw new CustomApiException(ErrorCode.ALREADY_EXIST_PHONE_NUMBER);
        }
        // 카카오 주소 API - 위도 경도 구하기
        Address address = kakaoAddressService.getKakaoAddress(requestDto.getAddress());
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        String qr = UUID.randomUUID().toString();
        User user = requestDto.toEntity(encodedPassword, address, qr);

        return new UserSignupResponseDto(userRepository.save(user));
    }

    public User signin(UserSigninRequestDto requestDto) {
        User user = userRepository.findByEmailAndDeletedAtIsNull(requestDto.getEmail())
                .orElseThrow(() -> new CustomApiException(ErrorCode.INCORRECT_EMAIL_OR_PASSWORD));
        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomApiException(ErrorCode.INCORRECT_EMAIL_OR_PASSWORD);
        }

        return user;
    }

    // 임시 유저 주소 기준 지도
    public UserMyPageResponseDto getUserMyPageKakaoAddressApi(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.INCORRECT_EMAIL_OR_PASSWORD));
        return new UserMyPageResponseDto(user);
    }

    // 유저 마이페이지
    public UserMyPageResponseDto getUserMyPage(User user) {
        return new UserMyPageResponseDto(user);
    }

    // 유저 마이쿠폰 보기
    public List<UserMyCouponsResponseDto> getUserMyCoupons(User user) {
        return couponRepository.findAllByUserId(user.getId())
                .stream()
                .map(UserMyCouponsResponseDto::new)
                .toList();
    }

    public UserUpdateResponseDto updateUser(User user, UserUpdateRequestDto requestDto) {
        Address address = kakaoAddressService.getKakaoAddress(requestDto.getAddress());
        user.update(address);
        return new UserUpdateResponseDto(userRepository.save(user));
    }

    public void deleteUser(User user, UserDeleteRequestDto requestDto) {
        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomApiException(ErrorCode.PASSWORD_MISS_MATCH);
        }

        user.delete(LocalDateTime.now());
        userRepository.save(user);
    }

    public User signupIfAbsent(String phone) {
        return userRepository.findByPhone(phone)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .phone(phone)
                                .build()
                ));
    }
}
