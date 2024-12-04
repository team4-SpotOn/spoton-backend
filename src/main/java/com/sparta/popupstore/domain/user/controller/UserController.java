package com.sparta.popupstore.domain.user.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.user.dto.request.UserSigninRequestDto;
import com.sparta.popupstore.domain.user.dto.response.UserMyCouponsResponseDto;
import com.sparta.popupstore.domain.user.dto.response.UserMypageResponseDto;
import com.sparta.popupstore.domain.user.dto.request.UserSignupRequestDto;
import com.sparta.popupstore.domain.user.dto.response.UserSignupResponseDto;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.sparta.popupstore.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저 API", description = "관리자, 고객에 대한 회원가입 로그인, 마이페이지 등의 API 입니다.")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponseDto> signup(
            @RequestBody @Valid UserSignupRequestDto requestDto,
            HttpServletResponse response
    ) {
        UserSignupResponseDto responseDto = userService.signup(requestDto);
        jwtUtil.addJwtToCookie(responseDto.getEmail(), response);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<Void> signin(
            @RequestBody UserSigninRequestDto requestDto,
            HttpServletResponse response
    ) {
        User user = userService.signin(requestDto);
        jwtUtil.addJwtToCookie(user.getEmail(), response);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "유저 마이페이지", description = "고객이 로그인 호 확인하는 마이페이지")
    @GetMapping("/mypage")
    public ResponseEntity<UserMypageResponseDto> getUserMypage(@AuthUser User user) {
        return ResponseEntity.ok(userService.getUserMypage(user.getId()));
    }

    @Operation(summary = "유저 내 쿠폰보기", description = "마이페이지에 본인이 가지고 있는 쿠폰 목록 확인")
    @GetMapping("/coupons")
    public ResponseEntity<List<UserMyCouponsResponseDto>> getUserMyCoupons(@AuthUser User user) {
        return ResponseEntity.ok(userService.getUserMyCoupons(user.getId()));
    }

}
