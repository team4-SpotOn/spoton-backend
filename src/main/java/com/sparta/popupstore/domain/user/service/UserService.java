package com.sparta.popupstore.domain.user.service;

import com.sparta.popupstore.config.PasswordEncoder;
import com.sparta.popupstore.domain.user.dto.request.UserSigninRequestDto;
import com.sparta.popupstore.domain.user.dto.request.UserSignupRequestDto;
import com.sparta.popupstore.domain.user.dto.response.UserSignupResponseDto;

import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.dto.response.UserMypageResponseDto;
import com.sparta.popupstore.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
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
    public UserMypageResponseDto getUserMypage(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() ->
            new IllegalArgumentException("해당 고객은 존재하지 않습니다.")
        );
        return new UserMypageResponseDto(user);
    }


    // 유저 마이쿠폰 보기
    public CouponResponseDto getUserMyCoupons(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() ->
            new IllegalArgumentException("해당 고객은 존재하지 않습니다.")
        );

        List<Object[]> couponData = userRepository.findByUserCoupons(userId);

        List<UserMyCouponsResponseDto> coupons = couponData.stream()
            .map(data -> new UserMyCouponsResponseDto((Long) data[0]))
            .collect(Collectors.toList());
        return new CouponResponseDto(coupons);

    }
}
