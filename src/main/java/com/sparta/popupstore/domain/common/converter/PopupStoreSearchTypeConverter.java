package com.sparta.popupstore.domain.common.converter;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.popupstore.enums.PopupStoreStatus;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PopupStoreSearchTypeConverter implements Converter<String, PopupStoreStatus> {
    @Override
    public PopupStoreStatus convert(String searchType) {
        try {
            return PopupStoreStatus.valueOf(searchType.toUpperCase());
        } catch(IllegalArgumentException e) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_NOT_SEARCH_TYPE);
        }
    }
}
