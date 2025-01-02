package com.sparta.popupstore.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

public class WebUtil {

    private static final int VIEW_TOKEN_LIFETIME = 60 * 60 * 24;

    public static boolean checkCookie(HttpServletRequest request, HttpServletResponse response, Long popupStoreId) {
        String cookieName = "viewedPopup_" + popupStoreId;

        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            return false;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookieName)) {
                return false;
            }
        }

        ResponseCookie cookie = ResponseCookie
                .from(cookieName)
                .maxAge(VIEW_TOKEN_LIFETIME)
                .httpOnly(true)
                .sameSite("Strict")  // 설정 고민해봐야될 듯...
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return true;
    }
}
