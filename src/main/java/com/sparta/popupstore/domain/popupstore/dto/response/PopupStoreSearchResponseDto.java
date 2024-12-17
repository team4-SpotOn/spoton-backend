package com.sparta.popupstore.domain.popupstore.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Getter
public class PopupStoreSearchResponseDto {
    private final List<PopupStoreGetAllResponseDto> popupStores;

    @Builder
    public PopupStoreSearchResponseDto(List<PopupStoreGetAllResponseDto> popupStores) {
        this.popupStores = popupStores;
    }
}
