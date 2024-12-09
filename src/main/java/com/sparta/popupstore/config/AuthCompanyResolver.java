package com.sparta.popupstore.config;

import com.sparta.popupstore.domain.common.annotation.AuthCompany;
import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.company.repository.CompanyRepository;
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
public class AuthCompanyResolver implements HandlerMethodArgumentResolver {

    private final CompanyRepository companyRepository;
    private final JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthCompany.class)
                && parameter.getParameterType().equals(Company.class);
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

        Claims companyInfo = jwtUtil.getInfoFromRequest(request);
        return companyRepository.findByEmailAndDeletedAtIsNull(companyInfo.getSubject())
                .orElseThrow(() -> new CustomApiException(ErrorCode.NEED_LOGIN));
    }
}
