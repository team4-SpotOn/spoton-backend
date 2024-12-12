package com.sparta.popupstore.domain.popupstore.dto.response;

import com.sparta.popupstore.domain.popupstore.entity.PopupStoreImage;
import lombok.Getter;

@Getter
public class PopupStoreImageResponseDto {
    private final Long id;
    private final String imageUrl;
    private final int sort;

    public PopupStoreImageResponseDto(PopupStoreImage popupStoreImage) {
        this.id = popupStoreImage.getId();
        this.imageUrl = popupStoreImage.getImageUrl();
        this.sort = popupStoreImage.getSort();
    }
}
