package com.sparta.popupstore.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.util.WebUtils;

public class WebUtil {

    private static final int VIEW_TOKEN_LIFETIME = 60 * 60 * 24;

    // 쿠키를 생성 및 응답에 추가
    public static void addCookie(HttpServletResponse response, String name) {
        ResponseCookie cookie = ResponseCookie.from(name)
                .path("/")
                .maxAge(VIEW_TOKEN_LIFETIME)
                .httpOnly(true)  // HTTP 전용으로 설정
                .sameSite("Strict")  // SameSite 속성 설정
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        return WebUtils.getCookie(request, name);
    }

    public static void deleteCookie(HttpServletResponse response, String name) {
        ResponseCookie cookie = ResponseCookie.from(name, "")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
