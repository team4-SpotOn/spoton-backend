package com.sparta.popupstore.domain.oauth2.controller;

import com.sparta.popupstore.domain.common.annotation.AuthSocialUser;
import com.sparta.popupstore.domain.oauth2.dto.ValidPhoneRequestDto;
import com.sparta.popupstore.domain.oauth2.entity.SocialUser;
import com.sparta.popupstore.domain.oauth2.service.OAuth2SigninService;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Tag(name = "소셜 로그인 API", description = "소셜 로그인과 유저 정보 요청을 위한 API")
@RequestMapping("/oauth2")
public class OAuth2SigninController {

    private final OAuth2SigninService oAuth2SigninService;
    private final JwtUtil jwtUtil;
    private static final String VALID_PHONE_URL = "http://localhost:8080/oAuth2CallbackPhoneNumber.html";

    @GetMapping("/signin/{platform}")
    public ResponseEntity<Void> redirectSigninPage(
            @PathVariable OAuth2Platform platform,
            HttpServletResponse response
    ) throws IOException {
        String signinPageUrl = oAuth2SigninService.generateSigninPageUrl(platform);
        response.sendRedirect(signinPageUrl);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/callback/{platform}")
    public ResponseEntity<Void> callback(
            @PathVariable OAuth2Platform platform,
            @RequestParam(name = "code") String authorizationCode,
            HttpServletResponse response
    ) throws IOException {
        SocialUser socialUser = oAuth2SigninService.signin(platform, authorizationCode);
        jwtUtil.addJwtToCookie(socialUser.getPlatformId(), socialUser.getPlatform(), response);
        if(socialUser.getUserId() == null) {
            response.sendRedirect(VALID_PHONE_URL);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping("/phone-number")
    public ResponseEntity<Void> validPhone(
            @AuthSocialUser SocialUser socialUser,
            @Valid @RequestBody ValidPhoneRequestDto requestDto,
            HttpServletResponse response
    ) {
        User user = oAuth2SigninService.validPhone(socialUser, requestDto);
        jwtUtil.addJwtToCookie(user.getEmail(), response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/callback/{platform}/token")
    public ResponseEntity<String> getAccessToken(
            @PathVariable OAuth2Platform platform,
            @RequestParam(name = "code") String authorizationCode
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(oAuth2SigninService.getAccessToken(platform, authorizationCode));
    }
}
