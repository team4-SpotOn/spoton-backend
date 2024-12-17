package com.sparta.popupstore.domain.popupstore.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PopupStoreSearchResponseDto {
    private final Page<PopupStoreGetAllResponseDto> popupStores;

    @Builder
    public PopupStoreSearchResponseDto(Page<PopupStoreGetAllResponseDto> popupStores) {
        this.popupStores = popupStores;
    }
}
