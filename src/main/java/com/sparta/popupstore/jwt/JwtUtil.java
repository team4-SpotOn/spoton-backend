package com.sparta.popupstore.jwt;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import com.sparta.popupstore.domain.user.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String USER_ROLE_KEY = "userRole";
    public static final String OAUTH2_PLATFORM_KEY = "oauth2Platform";
    public static final long TOKEN_LIFETIME = 1000 * 60 * 60 * 24 * 7; // 원활한 테스트를 위해 일주일로 설정.
    private static final String USER_SIGNUP_URL = "http://localhost:8080/signupPage.html";
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public void addJwtToCookie(String email, HttpServletResponse response) {
        addJwtToCookie(email, null, null, response);
    }

    public void addJwtToCookie(String email, UserRole userRole, HttpServletResponse response) {
        addJwtToCookie(email, userRole, null, response);
    }

    public void addJwtToCookie(String email, OAuth2Platform platform, HttpServletResponse response) {
        addJwtToCookie(email, null, platform, response);
    }

    public void addJwtToCookie(
            String email,
            UserRole userRole,
            OAuth2Platform platform,
            HttpServletResponse response
    ) {
        if(email == null || email.isEmpty()) {
            try {
                response.sendRedirect(USER_SIGNUP_URL);
            } catch(IOException e) {
                log.error(e.getMessage());
            }
            return;
        }

        Date now = new Date();

        String token = BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email)
                        .claim(USER_ROLE_KEY, userRole)
                        .claim(OAUTH2_PLATFORM_KEY, platform)
                        .setExpiration(new Date(now.getTime() + TOKEN_LIFETIME))
                        .setIssuedAt(now)
                        .signWith(key, signatureAlgorithm)
                        .compact();

        token = URLEncoder.encode(token, StandardCharsets.UTF_8)
                .replace("\\+", "%20");
        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public Claims getInfoFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            throw new CustomApiException(ErrorCode.TOKEN_NOT_FOUND);
        }

        String token = null;
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(AUTHORIZATION_HEADER)) {
                token = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                break;
            }
        }
        if(!StringUtils.hasText(token) || !token.startsWith(BEARER_PREFIX)) {
            throw new CustomApiException(ErrorCode.TOKEN_NOT_FOUND);
        }
        token = token.substring(BEARER_PREFIX.length());
        validateToken(token);

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch(Exception e) {
            throw new CustomApiException(ErrorCode.INVALID_TOKEN_ERROR);
        }
    }
}
