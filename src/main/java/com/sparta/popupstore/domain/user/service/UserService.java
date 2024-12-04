package com.sparta.popupstore.domain.user.service;

import com.sparta.popupstore.config.PasswordEncoder;
import com.sparta.popupstore.domain.promotionevent.entity.Coupon;
import com.sparta.popupstore.domain.promotionevent.repository.CouponRepository;
import com.sparta.popupstore.domain.user.dto.request.UserSigninRequestDto;
import com.sparta.popupstore.domain.user.dto.request.UserSignupRequestDto;
import com.sparta.popupstore.domain.user.dto.request.UserUpdateRequestDto;
import com.sparta.popupstore.domain.user.dto.response.UserMyCouponsResponseDto;
import com.sparta.popupstore.domain.user.dto.response.UserSignupResponseDto;

import com.sparta.popupstore.domain.user.dto.response.UserUpdateResponseDto;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.dto.response.UserMyPageResponseDto;
import com.sparta.popupstore.domain.user.repository.UserRepository;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CouponRepository couponRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSignupResponseDto signup(UserSignupRequestDto requestDto) {
        if(userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("Email address already in use");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = requestDto.toEntity(encodedPassword);
        return new UserSignupResponseDto(userRepository.save(user));
    }

    public User signin(UserSigninRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email address not found"));
        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        return user;
    }

    // 유저 마이페이지
    public UserMyPageResponseDto getUserMyPage(User user){
        return new UserMyPageResponseDto(user);
    }

    // 유저 마이쿠폰 보기
    public List<UserMyCouponsResponseDto> getUserMyCoupons(User user){
        List<Coupon> couponData = couponRepository.findByUserId(user.getId());
        return couponData.stream().map(UserMyCouponsResponseDto::new ).toList();
    }

    @Transactional
    public UserUpdateResponseDto updateUser(User user, UserUpdateRequestDto requestDto) {
        user.update(requestDto.getAddress());
        return new UserUpdateResponseDto(userRepository.saveAndFlush(user));
    }
}
