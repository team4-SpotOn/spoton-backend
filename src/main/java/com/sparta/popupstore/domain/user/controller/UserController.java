package com.sparta.popupstore.domain.user.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.user.dto.request.UserSigninRequestDto;
import com.sparta.popupstore.domain.user.dto.request.UserSignupRequestDto;
import com.sparta.popupstore.domain.user.dto.request.UserUpdateRequestDto;
import com.sparta.popupstore.domain.user.dto.response.UserMyCouponsResponseDto;
import com.sparta.popupstore.domain.user.dto.response.UserMyPageResponseDto;
import com.sparta.popupstore.domain.user.dto.response.UserSignupResponseDto;
import com.sparta.popupstore.domain.user.dto.response.UserUpdateResponseDto;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.service.UserService;
import com.sparta.popupstore.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저 API", description = "관리자, 고객에 대한 회원가입 로그인, 마이페이지 등의 API 입니다.")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "유저 회원 가입", description = "유저가 회원 가입 하는 API")
    @Parameter(name = "email", description = "계정 이메일")
    @Parameter(name = "password", description = "계정 비밀번호")
    @Parameter(name = "name", description = "본인 이름")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "유저 회원 가입 성공",
                    content = @Content(schema = @Schema(implementation = UserSignupResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "동일한 이메일을 가진 유저가 존재합니다."),
    })
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

    @Operation(summary = "유저 로그인", description = "유저가 로그인 하는 API")
    @Parameter(name = "email", description = "계정 이메일")
    @Parameter(name = "password", description = "계정 비밀번호")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "유저 로그인 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 이메일"),
            @ApiResponse(responseCode = "401", description = "비밀번호가 틀렸습니다.")
    })
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
    public ResponseEntity<UserMyPageResponseDto> getUserMyPage(@AuthUser User user) {
        return ResponseEntity.ok(userService.getUserMyPage(user));
    }

    @Operation(summary = "유저 내 쿠폰보기", description = "마이페이지에 본인이 가지고 있는 쿠폰 목록 확인")
    @GetMapping("/coupons")
    public ResponseEntity<List<UserMyCouponsResponseDto>> getUserMyCoupons(@AuthUser User user) {
        return ResponseEntity.ok(userService.getUserMyCoupons(user));
    }

    @Operation(summary = "유저 정보 수정", description = "유저 본인의 정보 수정")
    @PatchMapping
    public ResponseEntity<UserUpdateResponseDto> updateUser(
            @AuthUser User user,
            @RequestBody UserUpdateRequestDto requestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(user, requestDto));
    }
}
