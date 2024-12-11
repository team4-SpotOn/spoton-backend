package com.sparta.popupstore.domain.oauth2.controller;

import com.sparta.popupstore.domain.oauth2.entity.SocialUser;
import com.sparta.popupstore.domain.oauth2.service.OAuth2SigninService;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;
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

    @GetMapping("/oauth2/signin/{provider}")
    public void redirectSigninPage(
            @PathVariable OAuth2Provider provider,
            HttpServletResponse response
    ) throws IOException {
        String signinPageUrl = oAuth2SigninService.generateSigninPageUrl(provider);
        response.sendRedirect(signinPageUrl);
    }

    @GetMapping("/oauth2/callback/{provider}/test")
    public ResponseEntity<?> callbackTest(
            @PathVariable OAuth2Provider provider,
            @RequestParam(name = "code") String authorizationCode
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(oAuth2SigninService.callbackTest(provider, authorizationCode));
    }

    @GetMapping("/oauth2/callback/{provider}")
    public ResponseEntity<Void> callback(
            @PathVariable OAuth2Provider provider,
            @RequestParam(name = "code") String authorizationCode,
            HttpServletResponse response
    ) {
        SocialUser socialUser = oAuth2SigninService.signin(provider, authorizationCode);
        jwtUtil.addJwtToCookie(socialUser.getEmail(), response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
