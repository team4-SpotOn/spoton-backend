package com.sparta.popupstore.domain.user.controller;

import com.sparta.popupstore.domain.user.dto.response.UserMypageResponseDto;
import com.sparta.popupstore.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저 API", description = "관리자, 고객에 대한 회원가입 로그인, 마이페이지 등의 API 입니다.")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 마이페이지", description = "고객이 로그인 호 확인하는 마이페이지")
    @GetMapping("/mypage/{userId}")
    public ResponseEntity<UserMypageResponseDto> getUserMypage(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserMypage(userId));
    }
}
