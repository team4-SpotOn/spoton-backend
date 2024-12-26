package com.sparta.popupstore.domain.common.converter;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.oauth2.type.OAuth2Platform;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OAuth2PlatformConverter implements Converter<String, OAuth2Platform> {

    @Override
    public OAuth2Platform convert(String source) {
        try {
            return OAuth2Platform.valueOf(source.toUpperCase());
        } catch(IllegalArgumentException e) {
            throw new CustomApiException(ErrorCode.UNKNOWN_PLATFORM);
        }
    }
}
