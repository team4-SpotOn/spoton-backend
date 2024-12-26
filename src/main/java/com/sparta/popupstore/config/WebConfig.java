package com.sparta.popupstore.config;

import com.sparta.popupstore.domain.common.interceptor.CheckAdminInterceptor;
import com.sparta.popupstore.domain.common.resolver.AuthCompanyResolver;
import com.sparta.popupstore.domain.common.resolver.AuthSocialUserResolver;
import com.sparta.popupstore.domain.common.resolver.AuthUserResolver;
import com.sparta.popupstore.domain.common.converter.OAuth2PlatformConverter;
import com.sparta.popupstore.domain.common.converter.PopupStoreSearchTypeConverter;
import com.sparta.popupstore.domain.common.converter.S3DirectoryConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.client.RestClient;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthUserResolver authUserResolver;
    private final AuthCompanyResolver authCompanyResolver;
    private final AuthSocialUserResolver authSocialUserResolver;
    private final CheckAdminInterceptor checkAdminInterceptor;
    private final OAuth2PlatformConverter oAuth2PlatformConverter;
    private final PopupStoreSearchTypeConverter popupStoreSearchTypeConverter;
    private final S3DirectoryConverter s3DirectoryConverter;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authUserResolver);
        resolvers.add(authCompanyResolver);
        resolvers.add(authSocialUserResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkAdminInterceptor);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(oAuth2PlatformConverter);
        registry.addConverter(popupStoreSearchTypeConverter);
        registry.addConverter(s3DirectoryConverter);
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }
}
