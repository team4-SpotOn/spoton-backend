package com.sparta.popupstore.config;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.entity.UserRole;
import com.sparta.popupstore.domain.user.repository.UserRepository;
import com.sparta.popupstore.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthUserResolver implements HandlerMethodArgumentResolver {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthUser.class)
                && parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if(request == null) {
            throw new IllegalArgumentException("No request found");
        }

        Claims userInfo = jwtUtil.getUserInfoFromRequest(request);
        User user = userRepository.findByEmail(userInfo.getSubject())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(request.getRequestURI().startsWith("/promotionalEvents/admin") && !UserRole.ADMIN.equals(user.getUserRole())){
            throw new IllegalArgumentException("User has admin role");
        }
        return user;
    }
}
