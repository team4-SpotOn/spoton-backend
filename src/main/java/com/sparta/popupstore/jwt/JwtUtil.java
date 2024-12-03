package com.sparta.popupstore.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final long TOKEN_LIFETIME = 1000 * 60 * 60 * 24 * 7; // 원활한 테스트를 위해 일주일로 설정.

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public void addJwtToCookie(String email, HttpServletResponse response) {
        Date now = new Date();

        String token = BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email)
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

    public Claims getUserInfoFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            throw new RuntimeException("cookie is null");
        }

        String token = null;
        for(Cookie cookie : cookies) {
            if (cookie.getName().equals(AUTHORIZATION_HEADER)) {
                token = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                break;
            }
        }
        if(!StringUtils.hasText(token) || !token.startsWith(BEARER_PREFIX)) {
            throw new RuntimeException("invalid token");
        }
        token = token.substring(7);
        validateToken(token);

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException e) {
            throw new SecurityException("invalid token");
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("expired token");
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("unsupported token");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid token");
        }
    }
}
