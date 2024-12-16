package com.sparta.popupstore.domain.popupstore.repository;

import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreSearchResponseDto;
import org.springframework.data.domain.Pageable;

public interface PopupStoreQueryDsl{
    PopupStoreSearchResponseDto findByDate(Pageable pageable);
}
