package com.sparta.popupstore.domain.popupstore.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;


@Getter
public class PopupStoreSearchResponseDto {
    private final Page<PopupStoreGetAllResponseDto> startingSoonPopupStores;
    private final Page<PopupStoreGetAllResponseDto> endingSoonPopupStores;

    @Builder
    public PopupStoreSearchResponseDto(Page<PopupStoreGetAllResponseDto> startingSoonPopupStores, Page<PopupStoreGetAllResponseDto> endingSoonPopupStores) {
        this.startingSoonPopupStores = startingSoonPopupStores;
        this.endingSoonPopupStores = endingSoonPopupStores;
    }
}
