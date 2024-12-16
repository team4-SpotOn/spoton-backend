package com.sparta.popupstore.config;

import com.sparta.popupstore.domain.common.annotation.AuthSocialUser;
import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.oauth2.entity.SocialUser;
import com.sparta.popupstore.domain.oauth2.repository.SocialUserRepository;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import com.sparta.popupstore.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthSocialUserResolver implements HandlerMethodArgumentResolver {

    private final SocialUserRepository socialUserRepository;
    private final JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthSocialUser.class)
                && parameter.getParameterType().equals(SocialUser.class);
    }

    @Override
    public Object resolveArgument(
            @NonNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if(request == null) {
            throw new CustomApiException(ErrorCode.NEED_LOGIN);
        }

        Claims socialUserInfo = jwtUtil.getInfoFromRequest(request);

        String providerId = socialUserInfo.getSubject();
        OAuth2Platform platform = OAuth2Platform.valueOf((String) socialUserInfo.get(JwtUtil.OAUTH2_PLATFORM_KEY));
        return socialUserRepository.findByPlatformAndPlatformId(platform, providerId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.NEED_LOGIN));
    }
}
