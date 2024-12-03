package com.sparta.popupstore.domain.user.controller;

import com.sparta.popupstore.domain.user.dto.request.UserSignupRequestDto;
import com.sparta.popupstore.domain.user.dto.response.USerSignupResponseDto;
import com.sparta.popupstore.domain.user.service.UserService;
import com.sparta.popupstore.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<USerSignupResponseDto> signup(
            @RequestBody @Valid UserSignupRequestDto requestDto,
            HttpServletResponse response
    ) {
        USerSignupResponseDto responseDto = userService.signup(requestDto);
        jwtUtil.addJwtToCookie(responseDto.getEmail(), response);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }
}
