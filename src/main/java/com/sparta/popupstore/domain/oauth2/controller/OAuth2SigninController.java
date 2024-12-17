package com.sparta.popupstore.domain.oauth2.controller;

import com.sparta.popupstore.domain.common.annotation.AuthSocialUser;
import com.sparta.popupstore.domain.oauth2.dto.SigninUserRequestDto;
import com.sparta.popupstore.domain.oauth2.entity.SocialUser;
import com.sparta.popupstore.domain.oauth2.service.OAuth2SigninService;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class OAuth2SigninController {

    private final OAuth2SigninService oAuth2SigninService;
    private final JwtUtil jwtUtil;
    private final String signinUserUrl = "http://localhost:8080/oAuth2CallbackPhoneNumber.html";

    @GetMapping("/oauth2/signin/{platform}")
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

    @GetMapping("/oauth2/callback/{platform}")
    public ResponseEntity<Void> callback(
            @PathVariable OAuth2Platform platform,
            @RequestParam(name = "code") String authorizationCode,
            HttpServletResponse response
    ) throws IOException {
        SocialUser socialUser = oAuth2SigninService.signin(platform, authorizationCode);
        jwtUtil.addJwtToCookie(socialUser.getPlatformId(), socialUser.getPlatform(), response);
        if(socialUser.getUserId() == null) {
            response.sendRedirect(signinUserUrl);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @PostMapping(signinUserUrl)
    public ResponseEntity<Void> signinUser(
            @AuthSocialUser SocialUser socialUser,
            @RequestBody SigninUserRequestDto requestDto,
            HttpServletResponse response
    ) {
        User user = oAuth2SigninService.signinUser(socialUser, requestDto);
        jwtUtil.addJwtToCookie(user.getEmail(), response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/oauth2/callback/{platform}/token")
    public ResponseEntity<String> getAccessToken(
            @PathVariable OAuth2Platform platform,
            @RequestParam(name = "code") String authorizationCode
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(oAuth2SigninService.getAccessToken(platform, authorizationCode));
    }
}
