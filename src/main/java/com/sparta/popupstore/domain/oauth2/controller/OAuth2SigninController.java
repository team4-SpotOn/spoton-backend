package com.sparta.popupstore.domain.oauth2.controller;

import com.sparta.popupstore.domain.oauth2.entity.SocialUser;
import com.sparta.popupstore.domain.oauth2.service.OAuth2SigninService;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import com.sparta.popupstore.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class OAuth2SigninController {

    private final OAuth2SigninService oAuth2SigninService;
    private final JwtUtil jwtUtil;

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
    ) {
        SocialUser socialUser = oAuth2SigninService.signin(platform, authorizationCode);
        jwtUtil.addJwtToCookie(socialUser.getPlatformId(), socialUser.getPlatform(), response);
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
