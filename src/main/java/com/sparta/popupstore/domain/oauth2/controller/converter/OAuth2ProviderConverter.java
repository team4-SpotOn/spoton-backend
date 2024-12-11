package com.sparta.popupstore.domain.oauth2.controller.converter;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Provider;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OAuth2ProviderConverter implements Converter<String, OAuth2Provider> {

    @Override
    public OAuth2Provider convert(@NonNull String source) {
        try {
            return OAuth2Provider.valueOf(source.toUpperCase());
        } catch (RuntimeException e) {
            throw new CustomApiException(ErrorCode.UNKNOWN_PROVIDER);
        }
    }
}
