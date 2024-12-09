package com.sparta.popupstore.config;

import com.sparta.popupstore.domain.common.annotation.CheckAdmin;
import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.user.entity.UserRole;
import com.sparta.popupstore.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class CheckRoleInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        CheckAdmin checkAdmin = handlerMethod.getMethodAnnotation(CheckAdmin.class);
        if (checkAdmin == null) {
            return true;
        }

        Claims userInfo = jwtUtil.getInfoFromRequest(request);
        if (userInfo == null) {
            throw new CustomApiException(ErrorCode.NEED_LOGIN);
        }

        String userRole = userInfo.get(JwtUtil.USER_ROLE_KEY, String.class);
        if (userRole == null || !UserRole.valueOf(userRole).equals(UserRole.ADMIN)) {
            throw new CustomApiException(ErrorCode.NOT_ADMIN);
        }

        return true;
    }
}
